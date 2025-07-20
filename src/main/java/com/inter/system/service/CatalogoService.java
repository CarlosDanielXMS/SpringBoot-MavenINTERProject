// src/main/java/com/inter/system/service/CatalogoService.java
package com.inter.system.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.inter.system.model.Catalogo;
import com.inter.system.repository.CatalogoRepository;
import com.inter.system.model.CatalogoId;

@Service
@Transactional
public class CatalogoService {

    private final CatalogoRepository repo;

    public CatalogoService(CatalogoRepository repo) {
        this.repo = repo;
    }

    public List<Catalogo> listarTodos() {
        return repo.findAll();
    }

    public Catalogo buscarPorId(CatalogoId id) {
        return repo.findById(id)
                   .orElseThrow(() -> new IllegalArgumentException("Catálogo não encontrado: " + id));
    }

    public Catalogo salvar(Catalogo catalogo) {
        return repo.save(catalogo);
    }

    public void excluir(CatalogoId id) {
        repo.deleteById(id);
    }
}
