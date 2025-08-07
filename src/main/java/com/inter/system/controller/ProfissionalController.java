package com.inter.system.controller;

import com.inter.system.model.Profissional;
import com.inter.system.service.ProfissionalService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/profissionais")
public class ProfissionalController {

    private final ProfissionalService service;

    public ProfissionalController(ProfissionalService service) {
        this.service = service;
    }

    @ModelAttribute("activePage")
    public String activePage() {
        return "profissionais";
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("profissionais", service.listarAtivos());
        model.addAttribute("novoProfissional", new Profissional());
        return "profissionais";
    }

    @GetMapping("/novo")
    public String formNovo(Model model) {
        model.addAttribute("profissionais", service.listarAtivos());
        model.addAttribute("novoProfissional", new Profissional());
        return "profissionais";
    }

    @PostMapping
    public String criar(
        @Valid @ModelAttribute("novoProfissional") Profissional prof,
        BindingResult br,
        RedirectAttributes flash,
        Model model
    ) {
        if (br.hasErrors()) {
            model.addAttribute("profissionais", service.listarAtivos());
            return "profissionais";
        }
        service.criar(prof);
        flash.addFlashAttribute("mensagem", "Profissional criado com sucesso!");
        return "redirect:/profissionais";
    }

    @GetMapping("/{id}/editar")
    public String formEditar(@PathVariable Integer id, Model model) {
        model.addAttribute("profissionais", service.listarAtivos());
        model.addAttribute("novoProfissional", service.buscarPorId(id));
        return "profissionais";
    }

    @PutMapping("/{id}")
    public String atualizar(
        @PathVariable Integer id,
        @Valid @ModelAttribute("novoProfissional") Profissional prof,
        BindingResult br,
        RedirectAttributes flash,
        Model model
    ) {
        if (br.hasErrors()) {
            model.addAttribute("profissionais", service.listarAtivos());
            return "profissionais";
        }
        service.atualizar(id, prof);
        flash.addFlashAttribute("mensagem", "Profissional atualizado com sucesso!");
        return "redirect:/profissionais";
    }

    @GetMapping("/{id}/excluir")
    public String excluirViaGet(@PathVariable Integer id, RedirectAttributes flash) {
        service.inativar(id);
        flash.addFlashAttribute("mensagem", "Profissional inativado com sucesso!");
        return "redirect:/profissionais";
    }
}
