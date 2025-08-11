package com.inter.system.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inter.system.model.*;
import com.inter.system.service.*;
import org.hibernate.Hibernate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

@Controller
@RequestMapping("/agendamentos")
public class AgendaController {

    private final AgendaService agendaService;
    private final ClienteService clienteService;
    private final ProfissionalService profissionalService;
    private final ServicoService servicoService;
    private final ServicoAgendadoService servicoAgendadoService;
    private final ObjectMapper objectMapper;

    public AgendaController(AgendaService agendaService,
                            ClienteService clienteService,
                            ProfissionalService profissionalService,
                            ServicoService servicoService,
                            ServicoAgendadoService servicoAgendadoService,
                            ObjectMapper objectMapper) {
        this.agendaService = agendaService;
        this.clienteService = clienteService;
        this.profissionalService = profissionalService;
        this.servicoService = servicoService;
        this.servicoAgendadoService = servicoAgendadoService;
        this.objectMapper = objectMapper;
    }

    // ================== PÁGINA ==================
    @GetMapping
    @Transactional(readOnly = true)
    public String index(Model model) throws JsonProcessingException {
        var agendas = agendaService.listarAtivos();
        agendas.forEach(a -> {
            Hibernate.initialize(a.getCliente());
            Hibernate.initialize(a.getServicosAgendados());
            a.getServicosAgendados().forEach(sa -> {
                Hibernate.initialize(sa.getServico());
                Hibernate.initialize(sa.getProfissional());
            });
        });

        model.addAttribute("activePage", "agendamentos");
        model.addAttribute("agendasJson", objectMapper.writeValueAsString(agendas));
        model.addAttribute("clientesJson", objectMapper.writeValueAsString(clienteService.listarTodos()));
        model.addAttribute("servicosJson", objectMapper.writeValueAsString(servicoService.listarTodos()));
        model.addAttribute("profissionaisJson", objectMapper.writeValueAsString(profissionalService.listarTodos()));
        model.addAttribute("servicosAgendadosJson", objectMapper.writeValueAsString(servicoAgendadoService.listarTodos()));
        return "agendamentos";
    }

    // ================== MVC (opcional) ==================
    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Agenda agenda) {
        agendaService.salvar(agenda);
        return "redirect:/agendamentos";
    }
    @PostMapping("/{id}/atualizar")
    public String atualizar(@PathVariable Integer id, @ModelAttribute Agenda agenda) {
        agendaService.atualizar(id, agenda);
        return "redirect:/agendamentos";
    }
    @GetMapping("/{id}/excluir")
    public String excluir(@PathVariable Integer id) {
        agendaService.inativar(id);
        return "redirect:/agendamentos";
    }

    // ================== API REST ==================
    @GetMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Transactional(readOnly = true)
    public List<Agenda> apiListar() {
        var agendas = agendaService.listarAtivos();
        agendas.forEach(a -> {
            Hibernate.initialize(a.getCliente());
            Hibernate.initialize(a.getServicosAgendados());
            a.getServicosAgendados().forEach(sa -> {
                Hibernate.initialize(sa.getServico());
                Hibernate.initialize(sa.getProfissional());
            });
        });
        return agendas;
    }

    @GetMapping(value = "/api/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Transactional(readOnly = true)
    public Agenda apiObter(@PathVariable Integer id) {
        var a = agendaService.buscarPorId(id);
        Hibernate.initialize(a.getCliente());
        Hibernate.initialize(a.getServicosAgendados());
        a.getServicosAgendados().forEach(sa -> {
            Hibernate.initialize(sa.getServico());
            Hibernate.initialize(sa.getProfissional());
        });
        return a;
    }

    // Aceita QUALQUER Content-Type e corpo cru → parse robusto (evita 415 e 400 por data)
    @PostMapping(value = "/api", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> apiCriar(@RequestBody(required = false) byte[] bodyBytes) {
        try {
            Agenda agenda = parseAgendaBody(bodyBytes);
            var saved = agendaService.salvar(agenda);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (ResponseStatusException rse) {
            return ResponseEntity.status(rse.getStatusCode())
                    .body(Map.of("error", rse.getReason()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping(value = "/api/{id}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> apiAtualizar(@PathVariable Integer id,
                                          @RequestBody(required = false) byte[] bodyBytes) {
        try {
            Agenda agenda = parseAgendaBody(bodyBytes);
            var updated = agendaService.atualizar(id, agenda);
            return ResponseEntity.ok(updated);
        } catch (ResponseStatusException rse) {
            return ResponseEntity.status(rse.getStatusCode())
                    .body(Map.of("error", rse.getReason()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping(value = "/api/{id}")
    @ResponseBody
    public ResponseEntity<Void> apiInativar(@PathVariable Integer id) {
        agendaService.inativar(id);
        return ResponseEntity.noContent().build();
    }

    // Disponibilidade (já estava ok)
    @GetMapping(value = "/api/profissionais-disponiveis", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Transactional(readOnly = true)
    public List<Profissional> apiProfissionaisDisponiveis(
            @RequestParam("inicio") String inicioParam,
            @RequestParam("duracaoMin") Integer duracaoMin,
            @RequestParam(value = "servicoId", required = false) Integer servicoId) {

        if (duracaoMin == null || duracaoMin <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "duracaoMin inválido");
        }
        var inicio = parseDateTimeFlexible(inicioParam);
        if (inicio == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "inicio inválido: " + inicioParam);

        var base = (servicoId == null)
                ? profissionalService.listarTodos()
                : profissionalService.listarTodos().stream()
                  .filter(p -> p.getCatalogos() != null && p.getCatalogos().stream()
                      .anyMatch(c -> c.getServico() != null && c.getServico().getId().equals(servicoId)))
                  .toList();

        if (base.isEmpty()) return new ArrayList<>();

        var ids = base.stream().map(Profissional::getId).toList();
        var indisponiveis = agendaService.profissionaisIndisponiveis(ids, inicio, duracaoMin);
        return base.stream().filter(p -> !indisponiveis.contains(p.getId())).toList();
    }

    // ================== Helpers ==================
    private Agenda parseAgendaBody(byte[] bodyBytes) {
        if (bodyBytes == null || bodyBytes.length == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Corpo vazio");
        }
        String body = new String(bodyBytes, StandardCharsets.UTF_8);
        JsonNode root;
        try {
            root = objectMapper.readTree(body);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "JSON inválido: " + e.getMessage(), e);
        }

        Agenda a = new Agenda();

        // id (opcional)
        if (root.hasNonNull("id")) a.setId(root.get("id").asInt());

        // cliente
        JsonNode cli = root.path("cliente");
        if (cli.hasNonNull("id")) {
            Cliente c = new Cliente();
            c.setId(cli.get("id").asInt());
            a.setCliente(c);
        }

        // dataHora (formatos aceitos: 2025-08-10T08:00, 2025-08-10T08:00:00, etc.)
        String dh = textOrNull(root.path("dataHora"));
        LocalDateTime ldt = parseDateTimeFlexible(dh);
        if (ldt == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "dataHora inválido: " + dh);
        }
        a.setDataHora(ldt);

        // tempoTotal
        if (root.has("tempoTotal")) a.setTempoTotal(root.get("tempoTotal").asInt());

        // valorTotal
        if (root.has("valorTotal")) {
            BigDecimal v = asBigDecimal(root.get("valorTotal"));
            a.setValorTotal(v);
        }

        // status
        short st = (short) root.path("status").asInt(1);
        a.setStatus(st);

        // servicosAgendados
        List<ServicoAgendado> itens = new ArrayList<>();
        JsonNode arr = root.path("servicosAgendados");
        if (arr.isArray()) {
            for (JsonNode n : arr) {
                ServicoAgendado sa = new ServicoAgendado();

                JsonNode s = n.path("servico");
                if (s.hasNonNull("id")) {
                    Servico sv = new Servico();
                    sv.setId(s.get("id").asInt());
                    sa.setServico(sv);
                }

                JsonNode p = n.path("profissional");
                if (p.hasNonNull("id")) {
                    Profissional pr = new Profissional();
                    pr.setId(p.get("id").asInt());
                    sa.setProfissional(pr);
                }

                short stSa = (short) n.path("status").asInt(1);
                sa.setStatus(stSa);

                itens.add(sa);
            }
        }
        a.setServicosAgendados(itens);

        return a;
    }

    private String textOrNull(JsonNode n) {
        return (n != null && !n.isNull() && n.isTextual()) ? n.asText() : null;
    }

    private BigDecimal asBigDecimal(JsonNode n) {
        if (n == null || n.isNull()) return null;
        if (n.isNumber()) return BigDecimal.valueOf(n.asDouble());
        try {
            return new BigDecimal(n.asText().replace(",", "."));
        } catch (Exception e) {
            return null;
        }
    }

    private LocalDateTime parseDateTimeFlexible(String raw) {
        if (raw == null || raw.isBlank()) return null;
        String s = raw.trim();

        try { return OffsetDateTime.parse(s, DateTimeFormatter.ISO_DATE_TIME).toLocalDateTime(); }
        catch (DateTimeParseException ignored) {}

        try { return ZonedDateTime.parse(s, DateTimeFormatter.ISO_DATE_TIME).toLocalDateTime(); }
        catch (DateTimeParseException ignored) {}

        try { return LocalDateTime.parse(s, DateTimeFormatter.ISO_LOCAL_DATE_TIME); }
        catch (DateTimeParseException ignored) {}

        try { return LocalDateTime.parse(s, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")); }
        catch (DateTimeParseException ignored) {}

        try { return LocalDateTime.parse(s, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")); }
        catch (DateTimeParseException ignored) {}

        try { return LocalDateTime.parse(s, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); }
        catch (DateTimeParseException ignored) {}

        try { return LocalDateTime.ofInstant(Instant.parse(s), ZoneId.systemDefault()); }
        catch (DateTimeParseException ignored) {}

        return null;
    }
}
