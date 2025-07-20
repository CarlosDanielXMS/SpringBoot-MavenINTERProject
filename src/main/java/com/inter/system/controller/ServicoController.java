// src/main/java/com/inter/system/controller/ServicoController.java
package com.inter.system.controller;

import com.inter.system.model.Servico;
import com.inter.system.service.ServicoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/servicos")
public class ServicoController {

    private final ServicoService servicoService;

    public ServicoController(ServicoService servicoService) {
        this.servicoService = servicoService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("activePage", "servicos");
        model.addAttribute("servicos", servicoService.listarTodos());  // :contentReference[oaicite:26]{index=26}
        return "servicos";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Servico servico) {
        servicoService.salvar(servico);  // :contentReference[oaicite:27]{index=27}
        return "redirect:/servicos";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Integer id) {
        servicoService.excluir(id);  // :contentReference[oaicite:28]{index=28}
        return "redirect:/servicos";
    }
}
