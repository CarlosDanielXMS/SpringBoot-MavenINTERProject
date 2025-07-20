// src/main/java/com/inter/system/controller/CatalogoController.java
package com.inter.system.controller;

import com.inter.system.model.Catalogo;
import com.inter.system.model.CatalogoId;
import com.inter.system.service.CatalogoService;
import com.inter.system.service.ProfissionalService;
import com.inter.system.service.ServicoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/catalogos")
public class CatalogoController {

    private final CatalogoService service;
    private final ProfissionalService profissionalService;
    private final ServicoService servicoService;

    public CatalogoController(CatalogoService service,
                              ProfissionalService profissionalService,
                              ServicoService servicoService) {
        this.service = service;
        this.profissionalService = profissionalService;
        this.servicoService = servicoService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("catalogos", service.listarTodos());
        model.addAttribute("profissionais", profissionalService.listarTodos());
        model.addAttribute("servicos", servicoService.listarTodos());
        model.addAttribute("novoCatalogo", new Catalogo());
        return "catalogos";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute("novoCatalogo") Catalogo cat) {
        service.salvar(cat);
        return "redirect:/catalogos";
    }

    @GetMapping("/excluir/{profissionalId}/{servicoId}")
    public String excluir(@PathVariable Integer profissionalId,
                          @PathVariable Integer servicoId) {
        service.excluir(new CatalogoId(profissionalId, servicoId));
        return "redirect:/catalogos";
    }
}
