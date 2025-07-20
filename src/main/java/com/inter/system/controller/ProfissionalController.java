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

    private final ProfissionalService profissionalService;

    public ProfissionalController(ProfissionalService profissionalService) {
        this.profissionalService = profissionalService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("activePage", "profissionais");
        model.addAttribute("profissionais", profissionalService.listarTodos());  // :contentReference[oaicite:23]{index=23}
        return "profissionais";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Profissional profissional) {
        profissionalService.salvar(profissional);  // :contentReference[oaicite:24]{index=24}
        return "redirect:/profissionais";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Integer id) {
        profissionalService.excluir(id);  // :contentReference[oaicite:25]{index=25}
        return "redirect:/profissionais";
    }
}
