// src/main/java/com/inter/system/controller/ServicoAgendadoController.java
package com.inter.system.controller;

import com.inter.system.model.ServicoAgendado;
import com.inter.system.model.ServicoAgendadoId;
import com.inter.system.service.ServicoAgendadoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/servicos-agendados")
public class ServicoAgendadoController {

    private final ServicoAgendadoService saService;

    public ServicoAgendadoController(ServicoAgendadoService saService) {
        this.saService = saService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("servicosAgendados", saService.listarTodos());  // :contentReference[oaicite:32]{index=32}
        return "servicos-agendados";
    }

    @PostMapping("/salvar")
    public String salvar(
            @RequestParam Integer agendaId,
            @RequestParam Integer servicoId,
            @RequestParam Integer profissionalId,
            @ModelAttribute ServicoAgendado saForm
    ) {
        ServicoAgendadoId id = new ServicoAgendadoId(agendaId, servicoId, profissionalId);
        saForm.setId(id);
        saService.salvar(saForm);  // :contentReference[oaicite:33]{index=33}
        return "redirect:/servicos-agendados";
    }

    @GetMapping("/excluir")
    public String excluir(
            @RequestParam Integer agendaId,
            @RequestParam Integer servicoId,
            @RequestParam Integer profissionalId
    ) {
        ServicoAgendadoId id = new ServicoAgendadoId(agendaId, servicoId, profissionalId);
        saService.excluir(id);  // :contentReference[oaicite:34]{index=34}
        return "redirect:/servicos-agendados";
    }
}
