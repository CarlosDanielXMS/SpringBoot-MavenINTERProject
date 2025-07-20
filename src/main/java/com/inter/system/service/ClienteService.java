// src/main/java/com/inter/system/service/ClienteService.java
package com.inter.system.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.inter.system.model.Cliente;
import com.inter.system.repository.ClienteRepository;

@Service
@Transactional
public class ClienteService {

    private final ClienteRepository repo;

    public ClienteService(ClienteRepository repo) {
        this.repo = repo;
    }

    public List<Cliente> listarTodos() {
        return repo.findAll();
    }

    public Cliente buscarPorId(Integer id) {
        return repo.findById(id)
                   .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado: " + id));
    }

    public Cliente salvar(Cliente cliente) {
        return repo.save(cliente);
    }

    public void excluir(Integer id) {
        repo.deleteById(id);
    }
}
