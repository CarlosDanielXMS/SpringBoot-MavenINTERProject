// src/main/java/com/inter/system/controller/CatalogoController.java
package com.inter.system.controller;

import com.inter.system.model.Catalogo;
import com.inter.system.service.CatalogoService;
import com.inter.system.service.ProfissionalService;
import com.inter.system.service.ServicoService;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/catalogo")
public class CatalogoController {

    private final CatalogoService service;
    private final ProfissionalService profService;
    private final ServicoService servService;

    public CatalogoController(
            CatalogoService service,
            ProfissionalService profService,
            ServicoService servService) {
        this.service = service;
        this.profService = profService;
        this.servService = servService;
    }

    @ModelAttribute("activePage")
    public String activePage() {
        return "catalogo";
    }

    @ModelAttribute
    public void addCommons(Model model) {
        model.addAttribute("profissionais", profService.listarAtivos());
        model.addAttribute("servicos", servService.listarAtivos());
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("catalogo", service.listarAtivos());
        model.addAttribute("novoCatalogo", new Catalogo());
        return "catalogo";
    }

    @GetMapping("/novo")
    public String formNovo(Model model) {
        model.addAttribute("catalogo", service.listarAtivos());
        model.addAttribute("novoCatalogo", new Catalogo());
        return "catalogo";
    }

    @PostMapping
    public String criar(
            @Valid @ModelAttribute("novoCatalogo") Catalogo catalogo,
            BindingResult br,
            RedirectAttributes flash,
            Model model) {
        if (br.hasErrors()) {
            model.addAttribute("catalogo", service.listarAtivos());
            flash.addFlashAttribute("mensagem", "Erro ao criar novo item no Catálogo");
            return "catalogo";
        }
        catalogo.setProfissional(profService.buscarPorId(catalogo.getProfissional().getId()));
        catalogo.setServico(servService.buscarPorId(catalogo.getServico().getId()));

        service.criar(catalogo);
        flash.addFlashAttribute("mensagem", "Catálogo criado com sucesso!");
        return "redirect:/catalogo";
    }

    @GetMapping("/{idProfissional}/{idServico}/editar")
    public String formEditar(
            @PathVariable Integer idProfissional,
            @PathVariable Integer idServico,
            Model model) {
        model.addAttribute("catalogo", service.listarAtivos());
        model.addAttribute("novoCatalogo", service.buscarPorId(idProfissional, idServico));
        return "catalogo";
    }

    @PutMapping("/{idProfissional}/{idServico}")
    public String atualizar(
            @PathVariable Integer idProfissional,
            @PathVariable Integer idServico,
            @Valid @ModelAttribute("novoCatalogo") Catalogo catalogo,
            BindingResult br,
            Model model,
            RedirectAttributes flash) {
        if (br.hasErrors()) {
            model.addAttribute("catalogo", service.listarAtivos());
            return "catalogo";
        }
        service.atualizar(idProfissional, idServico, catalogo);
        flash.addFlashAttribute("mensagem", "Catálogo atualizado com sucesso!");
        return "redirect:/catalogo";
    }

    @GetMapping("/{idProfissional}/{idServico}/excluir")
    public String excluir(
            @PathVariable Integer idProfissional,
            @PathVariable Integer idServico,
            RedirectAttributes flash) {
        service.inativar(idProfissional, idServico);
        flash.addFlashAttribute("mensagem", "Catálogo inativado com sucesso!");
        return "redirect:/catalogo";
    }
}
