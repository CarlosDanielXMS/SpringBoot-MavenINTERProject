package com.inter.system.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.inter.system.model.Profissional;
import com.inter.system.repository.ProfissionalRepository;

@Service
@Transactional(readOnly = true)
public class ProfissionalService {

    private final ProfissionalRepository repo;

    public ProfissionalService(ProfissionalRepository repo) {
        this.repo = repo;
    }

    public List<Profissional> listarTodos() {
        return repo.findAll();
    }

    public List<Profissional> listarAtivos() {
        return repo.findByStatus((short)1);
    }

    public Profissional buscarPorId(Integer id) {
        return repo.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Profissional não encontrado: " + id));
    }

    @Transactional
    public Profissional criar(Profissional prof) {
        prof.setStatus((short)1);
        return repo.save(prof);
    }

    @Transactional
    public Profissional atualizar(Integer id, Profissional dados) {
        Profissional existente = buscarPorId(id);
        existente.setNome(dados.getNome());
        existente.setTelefone(dados.getTelefone());
        existente.setEmail(dados.getEmail());
        existente.setSalarioFixo(dados.getSalarioFixo());
        existente.setComissao(dados.getComissao());
        if (dados.getSenha() != null && !dados.getSenha().isBlank()) {
            existente.setSenha(dados.getSenha());
        }
        return repo.save(existente);
    }

    @Transactional
    public void inativar(Integer id) {
        Profissional existente = buscarPorId(id);
        existente.setStatus((short)2);
        repo.save(existente);
    }
}
