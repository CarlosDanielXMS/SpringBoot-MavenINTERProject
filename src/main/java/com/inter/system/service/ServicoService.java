// src/main/java/com/inter/system/service/ServicoService.java
package com.inter.system.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.inter.system.model.Servico;
import com.inter.system.repository.ServicoRepository;

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

    public Servico salvar(Servico servico) {
        return repo.save(servico);
    }

    public void excluir(Integer id) {
        repo.deleteById(id);
    }
}
