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

    private final ServicoService service;

    public ServicoController(ServicoService service) {
        this.service = service;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("servicos", service.listarTodos());
        model.addAttribute("novoServico", new Servico());
        return "servicos";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute("novoServico") Servico servico) {
        service.salvar(servico);
        return "redirect:/servicos";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        model.addAttribute("servicos", service.listarTodos());
        model.addAttribute("novoServico", service.buscarPorId(id));
        return "servicos";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Integer id) {
        service.excluir(id);
        return "redirect:/servicos";
    }
}
