// src/main/java/com/inter/system/controller/ClienteController.java
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
@RequestMapping("/cadastros")
public class CadastrosController {

    private final ClienteService service;

    public CadastrosController(ClienteService service) {
        this.service = service;
    }

    @ModelAttribute("activePage")
    public String activePage() {
        return "cadastros";
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("clientes", service.listarAtivos());
        model.addAttribute("novoCliente", new Cliente());
        return "cadastros";
    }

    @GetMapping("/novo")
    public String formNovo(Model model) {
        model.addAttribute("clientes", service.listarAtivos());
        model.addAttribute("novoCliente", new Cliente());
        return "cadastros";
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
        return "redirect:/cadastros";
    }

    @GetMapping("/{id}/editar")
    public String formEditar(@PathVariable Integer id, Model model) {
        model.addAttribute("clientes", service.listarAtivos());
        model.addAttribute("novoCliente", service.buscarPorId(id));
        return "cadastros";
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
            return "cadastros";
        }
        service.atualizar(id, cliente);
        flash.addFlashAttribute("mensagem", "Cliente atualizado com sucesso!");
        return "redirect:/cadastros";
    }

    @GetMapping("/{id}/excluir")
    public String excluirViaGet(@PathVariable Integer id, RedirectAttributes flash) {
        service.inativar(id);
        flash.addFlashAttribute("mensagem", "Cliente inativado com sucesso!");
        return "redirect:/cadastros";
    }
}
