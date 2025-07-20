package com.inter.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.inter.system.service.ClienteService;

@Controller
@RequestMapping("/clientes")
public class ClienteController {
    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("activePage", "clientes");
        model.addAttribute("clientes", clienteService.listarTodos());
        return "clientes";
    }

    // @PostMapping
    // public String criar(@ModelAttribute ClienteForm form) {
    //     clienteService.criar(form);
    //     return "redirect:/clientes";
    // }

    @GetMapping("/edit/{id}")
    public String editarForm(@PathVariable Integer id, Model model) {
        model.addAttribute("activePage", "clientes");
        model.addAttribute("cliente", clienteService.buscarPorId(id));
        return "cliente-form";
    }

    // @PostMapping("/update")
    // public String atualizar(@ModelAttribute ClienteForm form) {
    //     clienteService.atualizar(form);
    //     return "redirect:/clientes";
    // }

    @GetMapping("/delete/{id}")
    public String excluir(@PathVariable Integer id) {
        clienteService.excluir(id);
        return "redirect:/clientes";
    }
}
