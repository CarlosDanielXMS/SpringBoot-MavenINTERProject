// src/main/java/com/inter/system/service/AgendaService.java
package com.inter.system.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.inter.system.model.Agenda;
import com.inter.system.repository.AgendaRepository;

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
    
    public List<Agenda> listarAtivos() {
        return repo.findByStatus((short)1);
    }
}
