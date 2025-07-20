// src/main/java/com/inter/system/controller/ClienteController.java
package com.inter.system.controller;

import com.inter.system.model.Cliente;
import com.inter.system.service.ClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        model.addAttribute("clientes", clienteService.listarTodos());  // :contentReference[oaicite:20]{index=20}
        return "clientes";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Cliente cliente) {
        clienteService.salvar(cliente);  // :contentReference[oaicite:21]{index=21}
        return "redirect:/clientes";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Integer id) {
        clienteService.excluir(id);  // :contentReference[oaicite:22]{index=22}
        return "redirect:/clientes";
    }
}
