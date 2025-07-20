// src/main/java/com/inter/system/controller/ServicoAgendadoController.java
package com.inter.system.controller;

import com.inter.system.model.ServicoAgendado;
import com.inter.system.model.ServicoAgendadoId;
import com.inter.system.service.AgendaService;
import com.inter.system.service.ProfissionalService;
import com.inter.system.service.ServicoAgendadoService;
import com.inter.system.service.ServicoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/servicos-agendados")
public class ServicoAgendadoController {

    private final ServicoAgendadoService service;
    private final AgendaService agendaService;
    private final ServicoService servicoService;
    private final ProfissionalService profissionalService;

    public ServicoAgendadoController(ServicoAgendadoService service,
                                     AgendaService agendaService,
                                     ServicoService servicoService,
                                     ProfissionalService profissionalService) {
        this.service = service;
        this.agendaService = agendaService;
        this.servicoService = servicoService;
        this.profissionalService = profissionalService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("servicosAgendados", service.listarTodos());
        model.addAttribute("agendas", agendaService.listarTodos());
        model.addAttribute("servicos", servicoService.listarTodos());
        model.addAttribute("profissionais", profissionalService.listarTodos());
        model.addAttribute("novoServicoAgendado", new ServicoAgendado());
        return "servicosAgendados";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute("novoServicoAgendado") ServicoAgendado sa) {
        service.salvar(sa);
        return "redirect:/servicos-agendados";
    }

    @GetMapping("/excluir/{idAgenda}/{idServico}/{idProfissional}")
    public String excluir(@PathVariable Integer idAgenda,
                          @PathVariable Integer idServico,
                          @PathVariable Integer idProfissional) {
        service.excluir(new ServicoAgendadoId(idAgenda, idServico, idProfissional));
        return "redirect:/servicos-agendados";
    }
}
