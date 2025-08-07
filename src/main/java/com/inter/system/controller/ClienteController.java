package com.inter.system.controller;

import com.inter.system.model.Cliente;
import com.inter.system.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @ModelAttribute("activePage")
    public String activePage() {
        return "clientes";
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("clientes", service.listarAtivos());
        model.addAttribute("novoCliente", new Cliente());
        return "clientes";
    }

    @GetMapping("/novo")
    public String formNovo(Model model) {
        model.addAttribute("clientes", service.listarAtivos());
        model.addAttribute("novoCliente", new Cliente());
        return "clientes";
    }

    @PostMapping
    public String criar(
        @Valid @ModelAttribute("novoCliente") Cliente cliente,
        BindingResult br,
        RedirectAttributes flash,
        Model model
    ) {
        if (br.hasErrors()) {
            model.addAttribute("clientes", service.listarAtivos());
            return "clientes";
        }
        service.criar(cliente);
        flash.addFlashAttribute("mensagem", "Cliente criado com sucesso!");
        return "redirect:/clientes";
    }

    @GetMapping("/{id}/editar")
    public String formEditar(@PathVariable Integer id, Model model) {
        model.addAttribute("clientes", service.listarAtivos());
        model.addAttribute("novoCliente", service.buscarPorId(id));
        return "clientes";
    }

    @PutMapping("/{id}")
    public String atualizar(
        @PathVariable Integer id,
        @Valid @ModelAttribute("novoCliente") Cliente cliente,
        BindingResult br,
        RedirectAttributes flash,
        Model model
    ) {
        if (br.hasErrors()) {
            model.addAttribute("clientes", service.listarAtivos());
            return "clientes";
        }
        service.atualizar(id, cliente);
        flash.addFlashAttribute("mensagem", "Cliente atualizado com sucesso!");
        return "redirect:/clientes";
    }

    @GetMapping("/{id}/excluir")
    public String excluirViaGet(@PathVariable Integer id, RedirectAttributes flash) {
        service.inativar(id);
        flash.addFlashAttribute("mensagem", "Cliente inativado com sucesso!");
        return "redirect:/clientes";
    }
}
