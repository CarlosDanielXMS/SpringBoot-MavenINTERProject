// ServicoService.java
package com.inter.system.service;

import com.inter.system.model.Servico;
import com.inter.system.repository.ServicoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ServicoService {

    private final ServicoRepository repo;

    public ServicoService(ServicoRepository repo) {
        this.repo = repo;
    }

    public List<Servico> listarTodos() {
        return repo.findAll();
    }

    public Servico buscarPorId(Integer id) {
        return repo.findById(id)
                   .orElseThrow(() -> new IllegalArgumentException("Serviço não encontrado: " + id));
    }

    public Servico criar(Servico servico) {
        return repo.save(servico);
    }

    public Servico atualizar(Servico servico) {
        buscarPorId(servico.getId());
        return repo.save(servico);
    }

    public void excluir(Integer id) {
        repo.deleteById(id);
    }
}
