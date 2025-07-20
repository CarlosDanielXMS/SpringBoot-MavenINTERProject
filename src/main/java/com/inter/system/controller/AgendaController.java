// src/main/java/com/inter/system/controller/AgendaController.java
package com.inter.system.controller;

import com.inter.system.model.Agenda;
import com.inter.system.service.AgendaService;
import com.inter.system.service.ClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/agendamentos")
public class AgendaController {

    private final AgendaService service;
    private final ClienteService clienteService;

    public AgendaController(AgendaService service, ClienteService clienteService) {
        this.service = service;
        this.clienteService = clienteService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("agendamentos", service.listarTodos());
        model.addAttribute("clientes", clienteService.listarTodos());
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
