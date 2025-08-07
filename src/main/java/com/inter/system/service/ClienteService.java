package com.inter.system.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.inter.system.model.Cliente;
import com.inter.system.repository.ClienteRepository;

@Service
@Transactional(readOnly = true)
public class ClienteService {

    private final ClienteRepository repo;

    public ClienteService(ClienteRepository repo) {
        this.repo = repo;
    }

    public List<Cliente> listarTodos() {
        return repo.findAll();
    }

    public List<Cliente> listarAtivos() {
        return repo.findByStatus((short)1);
    }

    public Cliente buscarPorId(Integer id) {
        return repo.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado: " + id));
    }

    @Transactional
    public Cliente criar(Cliente cliente) {
        cliente.setStatus((short)1);
        return repo.save(cliente);
    }

    @Transactional
    public Cliente atualizar(Integer id, Cliente dados) {
        Cliente existente = buscarPorId(id);
        existente.setNome(dados.getNome());
        existente.setTelefone(dados.getTelefone());
        existente.setEmail(dados.getEmail());
        if (dados.getSenha() != null && !dados.getSenha().isBlank()) {
            existente.setSenha(dados.getSenha());
        }
        return repo.save(existente);
    }

    @Transactional
    public void inativar(Integer id) {
        Cliente existente = buscarPorId(id);
        existente.setStatus((short)2);
        repo.save(existente);
    }
}
