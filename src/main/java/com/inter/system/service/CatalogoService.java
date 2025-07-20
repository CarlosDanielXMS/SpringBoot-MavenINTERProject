// CatalogoService.java
package com.inter.system.service;

import com.inter.system.model.Catalogo;
import com.inter.system.model.CatalogoId;
import com.inter.system.repository.CatalogoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public Catalogo buscarPorId(Integer idProfissional, Integer idServico) {
        return repo.findById(new CatalogoId(idProfissional, idServico))
                   .orElseThrow(() -> new IllegalArgumentException("Catálogo não encontrado"));
    }

    public Catalogo criar(Catalogo catalogo) {
        return repo.save(catalogo);
    }

    public Catalogo atualizar(Catalogo catalogo) {
        buscarPorId(catalogo.getProfissional().getId(), catalogo.getServico().getId());
        return repo.save(catalogo);
    }

    public void excluir(Integer idProfissional, Integer idServico) {
        repo.deleteById(new CatalogoId(idProfissional, idServico));
    }
}
