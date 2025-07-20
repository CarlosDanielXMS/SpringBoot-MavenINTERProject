// AgendaService.java
package com.inter.system.service;

import com.inter.system.model.Agenda;
import com.inter.system.repository.AgendaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AgendaService {

    private final AgendaRepository repo;

    public AgendaService(AgendaRepository repo) {
        this.repo = repo;
    }

    public List<Agenda> listarTodos() {
        return repo.findAll();
    }

    public Agenda buscarPorId(Integer id) {
        return repo.findById(id)
                   .orElseThrow(() -> new IllegalArgumentException("Agenda não encontrada: " + id));
    }

    public Agenda criar(Agenda agenda) {
        return repo.save(agenda);
    }

    public Agenda atualizar(Agenda agenda) {
        buscarPorId(agenda.getId());
        return repo.save(agenda);
    }

    public void excluir(Integer id) {
        repo.deleteById(id);
    }
}
