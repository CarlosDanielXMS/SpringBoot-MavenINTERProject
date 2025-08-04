// src/main/java/com/inter/system/service/ServicoAgendadoService.java
package com.inter.system.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.inter.system.model.ServicoAgendado;
import com.inter.system.repository.ServicoAgendadoRepository;

@Service
@Transactional
public class ServicoAgendadoService {

    private final ServicoAgendadoRepository repo;

    public ServicoAgendadoService(ServicoAgendadoRepository repo) {
        this.repo = repo;
    }

    public List<ServicoAgendado> listarTodos() {
        return repo.findAll();
    }
}
