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
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    // 1. LISTAR
    @GetMapping
    public String listar(Model model,
                         @ModelAttribute("novoCliente") Cliente novoCliente) {
        model.addAttribute("clientes", service.listarTodos());
        return "clientes";
    }

    // 2. CRIAR
    @PostMapping
    public String criar(@Valid @ModelAttribute("novoCliente") Cliente cliente,
                        BindingResult br,
                        RedirectAttributes flash,
                        Model model) {
        if (br.hasErrors()) {
            model.addAttribute("clientes", service.listarTodos());
            return "clientes";
        }
        service.salvar(cliente);
        flash.addFlashAttribute("mensagem", "Cliente criado com sucesso!");
        return "redirect:/clientes";
    }

    // 3. FORMULÁRIO DE EDIÇÃO
    @GetMapping("/{id}/editar")
    public String formEditar(@PathVariable Integer id, Model model) {
        model.addAttribute("clientes", service.listarTodos());
        model.addAttribute("novoCliente", service.buscarPorId(id));
        return "clientes";
    }

    // 4. ATUALIZAR
    @PutMapping("/{id}")
    public String atualizar(@PathVariable Integer id,
                            @Valid @ModelAttribute("novoCliente") Cliente cliente,
                            BindingResult br,
                            RedirectAttributes flash,
                            Model model) {
        if (br.hasErrors()) {
            model.addAttribute("clientes", service.listarTodos());
            return "clientes";
        }
        cliente.setId(id);
        service.salvar(cliente);
        flash.addFlashAttribute("mensagem", "Cliente atualizado com sucesso!");
        return "redirect:/clientes";
    }

    // 5. DELETAR
    @DeleteMapping("/{id}")
    public String deletar(@PathVariable Integer id,
                          RedirectAttributes flash) {
        service.excluir(id);
        flash.addFlashAttribute("mensagem", "Cliente excluído com sucesso!");
        return "redirect:/clientes";
    }
}
