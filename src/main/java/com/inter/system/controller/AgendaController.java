package com.inter.system.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inter.system.service.AgendaService;
import com.inter.system.service.ClienteService;
import com.inter.system.service.ProfissionalService;
import com.inter.system.service.ServicoService;
import com.inter.system.service.ServicoAgendadoService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/agendamentos")
public class AgendaController {

    private final AgendaService agendaService;
    private final ClienteService clienteService;
    private final ServicoService servicoService;
    private final ProfissionalService profissionalService;
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
        this.servicoService = servicoService;
        this.profissionalService = profissionalService;
        this.servicoAgendadoService = servicoAgendadoService;
        this.objectMapper = objectMapper;
    }

    @ModelAttribute("activePage")
    public String activePage() {
        return "agendamentos";
    }

    @GetMapping
    public String listar(Model model) throws JsonProcessingException {
        model.addAttribute("agendasJson", objectMapper.writeValueAsString(agendaService.listarTodos()));
        model.addAttribute("clientesJson", objectMapper.writeValueAsString(clienteService.listarTodos()));
        model.addAttribute("servicosJson", objectMapper.writeValueAsString(servicoService.listarTodos()));
        model.addAttribute("profissionaisJson", objectMapper.writeValueAsString(profissionalService.listarTodos()));
        model.addAttribute("servicosAgendadosJson", objectMapper.writeValueAsString(servicoAgendadoService.listarTodos()));

        return "agendamentos";
    }
}
