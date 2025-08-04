package com.inter.system.controller;

import com.inter.system.service.AgendaService;
import com.inter.system.service.ClienteService;
import com.inter.system.service.ProfissionalService;
import com.inter.system.service.ServicoService;

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

    public AgendaController(AgendaService agendaService, ClienteService clienteService,
            ProfissionalService profissionalService, ServicoService servicoService) {
        this.agendaService = agendaService;
        this.clienteService = clienteService;
        this.servicoService = servicoService;
        this.profissionalService = profissionalService;
    }

    @ModelAttribute("activePage")
    public String activePage() {
        return "agendamentos";
    }

    @GetMapping
    public String listar(Model model) {

        model.addAttribute("agendas", agendaService.listarTodos());
        model.addAttribute("clientes", clienteService.listarTodos());
        model.addAttribute("servicos", servicoService.listarTodos());
        model.addAttribute("profissionais", profissionalService.listarTodos());

        return "agendamentos";
    }
}
