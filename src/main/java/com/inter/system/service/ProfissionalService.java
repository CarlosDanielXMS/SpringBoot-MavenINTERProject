// ProfissionalService.java
package com.inter.system.service;

import com.inter.system.model.Profissional;
import com.inter.system.repository.ProfissionalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProfissionalService {

    private final ProfissionalRepository repo;

    public ProfissionalService(ProfissionalRepository repo) {
        this.repo = repo;
    }

    public List<Profissional> listarTodos() {
        return repo.findAll();
    }

    public Profissional buscarPorId(Integer id) {
        return repo.findById(id)
                   .orElseThrow(() -> new IllegalArgumentException("Profissional não encontrado: " + id));
    }

    public Profissional criar(Profissional profissional) {
        return repo.save(profissional);
    }

    public Profissional atualizar(Profissional profissional) {
        buscarPorId(profissional.getId());
        return repo.save(profissional);
    }

    public void excluir(Integer id) {
        repo.deleteById(id);
    }
}
