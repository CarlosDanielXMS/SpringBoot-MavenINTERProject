package com.inter.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.inter.system.service.AgendaService;
import com.inter.system.service.ClienteService;
import com.inter.system.service.ProfissionalService;
import com.inter.system.service.ServicoService;

@Controller
@RequestMapping("/agendamentos")
public class AgendamentoController {
    private final AgendaService agendaService;
    private final ClienteService clienteService;
    private final ServicoService servicoService;
    private final ProfissionalService profissionalService;

    public AgendamentoController(AgendaService agendaService,
    ClienteService clienteService,
    ServicoService servicoService,
    ProfissionalService profissionalService) {
        this.agendaService = agendaService;
        this.clienteService = clienteService;
        this.servicoService = servicoService;
        this.profissionalService = profissionalService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("activePage", "agendamentos");
        model.addAttribute("agendamentos", agendaService.listarTodos());
        model.addAttribute("clientes", clienteService.listarTodos());
        model.addAttribute("servicos", servicoService.listarTodos());
        model.addAttribute("profissionais", profissionalService.listarTodos());
        return "agendamentos";
    }

    // @PostMapping
    // public String criar(@ModelAttribute AgendaForm form) {
    //     agendaService.criar(form);
    //     return "redirect:/agendamentos";
    // }

    @GetMapping("/edit/{id}")
    public String editarForm(@PathVariable Integer id, Model model) {
        model.addAttribute("activePage", "agendamentos");
        model.addAttribute("agenda", agendaService.buscarPorId(id));
        model.addAttribute("clientes", clienteService.listarTodos());
        model.addAttribute("servicos", servicoService.listarTodos());
        model.addAttribute("profissionais", profissionalService.listarTodos());
        return "agendamento-form";
    }

    // @PostMapping("/update")
    // public String atualizar(@ModelAttribute AgendaForm form) {
    //     agendaService.atualizar(form);
    //     return "redirect:/agendamentos";
    // }

    @GetMapping("/delete/{id}")
    public String excluir(@PathVariable Integer id) {
        agendaService.excluir(id);
        return "redirect:/agendamentos";
    }

}
