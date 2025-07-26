package com.inter.system.controller;

import com.inter.system.model.Servico;
import com.inter.system.service.ServicoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/servicos")
public class ServicoController {

    private final ServicoService service;

    public ServicoController(ServicoService service) {
        this.service = service;
    }

    @ModelAttribute("activePage")
    public String activePage() {
        return "servicos";
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("servicos", service.listarAtivos());
        model.addAttribute("novoServico", new Servico());
        return "servicos";
    }

    @GetMapping("/novo")
    public String formNovo(Model model) {
        model.addAttribute("servicos", service.listarAtivos());
        model.addAttribute("novoServico", new Servico());
        return "servicos";
    }

    @PostMapping
    public String criar(
        @Valid @ModelAttribute("novoServico") Servico servico,
        BindingResult br,
        RedirectAttributes flash,
        Model model
    ) {
        if (br.hasErrors()) {
            model.addAttribute("servicos", service.listarAtivos());
            return "servicos";
        }
        service.criar(servico);
        flash.addFlashAttribute("mensagem", "Serviço criado com sucesso!");
        return "redirect:/servicos";
    }

    @GetMapping("/{id}/editar")
    public String formEditar(@PathVariable Integer id, Model model) {
        model.addAttribute("servicos", service.listarAtivos());
        model.addAttribute("novoServico", service.buscarPorId(id));
        return "servicos";
    }

    @PutMapping("/{id}")
    public String atualizar(
        @PathVariable Integer id,
        @Valid @ModelAttribute("novoServico") Servico servico,
        BindingResult br,
        RedirectAttributes flash,
        Model model
    ) {
        if (br.hasErrors()) {
            model.addAttribute("servicos", service.listarAtivos());
            return "servicos";
        }
        service.atualizar(id, servico);
        flash.addFlashAttribute("mensagem", "Serviço atualizado com sucesso!");
        return "redirect:/servicos";
    }

    @GetMapping("/{id}/excluir")
    public String excluirViaGet(@PathVariable Integer id, RedirectAttributes flash) {
        service.inativar(id);
        flash.addFlashAttribute("mensagem", "Serviço inativado com sucesso!");
        return "redirect:/servicos";
    }
}
