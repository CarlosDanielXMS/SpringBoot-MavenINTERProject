// src/main/java/com/inter/system/controller/AgendaController.java
package com.inter.system.controller;

import com.inter.system.model.Agenda;
import com.inter.system.service.AgendaService;
import com.inter.system.service.ClienteService;
import com.inter.system.service.ProfissionalService;
import com.inter.system.service.ServicoAgendadoService;
import com.inter.system.service.ServicoService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/agendamentos")
public class AgendaController {

    private final ClienteService clienteService;
    private final ProfissionalService profissionalService;
    private final ServicoService servicoService;
    private final AgendaService service;
    private final ServicoAgendadoService servicoAgendadoService;

    public AgendaController(
        ClienteService clienteService,
        ProfissionalService profissionalService,
        ServicoService servicoService,
        AgendaService service,
        ServicoAgendadoService servicoAgendadoService
    ) {
        this.clienteService = clienteService;
        this.profissionalService = profissionalService;
        this.servicoService = servicoService;
        this.service = service;
        this.servicoAgendadoService = servicoAgendadoService;
    }

    @ModelAttribute("activePage")
    public String activePage() {
        return "agendamentos";
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("clientes", clienteService.listarAtivos());
        model.addAttribute("profissionais", profissionalService.listarAtivos());
        model.addAttribute("servicos", servicoService.listarAtivos());
        model.addAttribute("agendas", service.listarTodos());
        model.addAttribute("servicosAgendados", servicoAgendadoService.listarTodos());
        model.addAttribute("novoAgendamento", new Agenda());
        return "agendamentos";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute("novoAgendamento") Agenda ag) {
        service.salvar(ag);
        return "redirect:/agendamentos";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Integer id) {
        service.excluir(id);
        return "redirect:/agendamentos";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        model.addAttribute("agendamentos", service.listarTodos());
        model.addAttribute("clientes", clienteService.listarTodos());
        model.addAttribute("novoAgendamento", service.buscarPorId(id));
        return "agendamentos";
    }
}
