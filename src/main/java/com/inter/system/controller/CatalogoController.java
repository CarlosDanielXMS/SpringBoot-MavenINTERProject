// src/main/java/com/inter/system/controller/CatalogoController.java
package com.inter.system.controller;

import com.inter.system.model.Catalogo;
import com.inter.system.model.CatalogoId;
import com.inter.system.service.CatalogoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/catalogos")
public class CatalogoController {

    private final CatalogoService catalogoService;

    public CatalogoController(CatalogoService catalogoService) {
        this.catalogoService = catalogoService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("catalogos", catalogoService.listarTodos());  // :contentReference[oaicite:35]{index=35}
        return "catalogos";
    }

    @PostMapping("/salvar")
    public String salvar(
            @RequestParam Integer profissionalId,
            @RequestParam Integer servicoId,
            @ModelAttribute Catalogo catForm
    ) {
        CatalogoId id = new CatalogoId(profissionalId, servicoId);
        catForm.setId(id);
        catalogoService.salvar(catForm);  // :contentReference[oaicite:36]{index=36}
        return "redirect:/catalogos";
    }

    @GetMapping("/excluir")
    public String excluir(
            @RequestParam Integer profissionalId,
            @RequestParam Integer servicoId
    ) {
        CatalogoId id = new CatalogoId(profissionalId, servicoId);
        catalogoService.excluir(id);  // :contentReference[oaicite:37]{index=37}
        return "redirect:/catalogos";
    }
}
