package com.inter.system.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.inter.system.model.Servico;
import com.inter.system.repository.ServicoRepository;

@Service
@Transactional(readOnly = true)
public class ServicoService {

    private final ServicoRepository repo;

    public ServicoService(ServicoRepository repo) {
        this.repo = repo;
    }

    public List<Servico> listarTodos() {
        return repo.findAll();
    }

    public List<Servico> listarAtivos() {
        return repo.findByStatus((short)1);
    }

    public Servico buscarPorId(Integer id) {
        return repo.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Serviço não encontrado: " + id));
    }

    @Transactional
    public Servico criar(Servico servico) {
        servico.setStatus((short)1);
        return repo.save(servico);
    }

    @Transactional
    public Servico atualizar(Integer id, Servico dados) {
        Servico existente = buscarPorId(id);
        existente.setDescricao(dados.getDescricao());
        existente.setValor(dados.getValor());
        existente.setTempoMedio(dados.getTempoMedio());
        return repo.save(existente);
    }

    @Transactional
    public void inativar(Integer id) {
        Servico existente = buscarPorId(id);
        existente.setStatus((short)2);
        repo.save(existente);
    }
}
