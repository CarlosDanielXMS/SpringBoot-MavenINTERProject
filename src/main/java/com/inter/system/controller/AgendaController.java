// src/main/java/com/inter/system/controller/AgendaController.java
package com.inter.system.controller;

import com.inter.system.model.Agenda;
import com.inter.system.service.AgendaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/agendamentos")
public class AgendaController {

    private final AgendaService agendaService;

    public AgendaController(AgendaService agendaService) {
        this.agendaService = agendaService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("activePage", "agendamentos");
        model.addAttribute("agendas", agendaService.listarTodos());  // :contentReference[oaicite:29]{index=29}
        return "agendamentos";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Agenda agenda) {
        agendaService.salvar(agenda);  // :contentReference[oaicite:30]{index=30}
        return "redirect:/agendamentos";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Integer id) {
        agendaService.excluir(id);  // :contentReference[oaicite:31]{index=31}
        return "redirect:/agendamentos";
    }
}
