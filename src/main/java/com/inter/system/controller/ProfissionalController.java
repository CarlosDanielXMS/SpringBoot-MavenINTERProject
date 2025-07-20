// src/main/java/com/inter/system/controller/ProfissionalController.java
package com.inter.system.controller;

import com.inter.system.model.Profissional;
import com.inter.system.service.ProfissionalService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/profissionais")
public class ProfissionalController {

    private final ProfissionalService service;

    public ProfissionalController(ProfissionalService service) {
        this.service = service;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("profissionais", service.listarTodos());
        model.addAttribute("novoProfissional", new Profissional());
        return "profissionais";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute("novoProfissional") Profissional prof) {
        service.salvar(prof);
        return "redirect:/profissionais";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        model.addAttribute("profissionais", service.listarTodos());
        model.addAttribute("novoProfissional", service.buscarPorId(id));
        return "profissionais";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Integer id) {
        service.excluir(id);
        return "redirect:/profissionais";
    }
}
