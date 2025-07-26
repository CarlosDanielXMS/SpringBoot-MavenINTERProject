// src/main/java/com/inter/system/service/CatalogoService.java
package com.inter.system.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.inter.system.model.Catalogo;
import com.inter.system.model.CatalogoId;
import com.inter.system.repository.CatalogoRepository;

@Service
@Transactional(readOnly = true)
public class CatalogoService {

    private final CatalogoRepository repo;

    public CatalogoService(CatalogoRepository repo) {
        this.repo = repo;
    }
    
    public List<Catalogo> listarTodos() {
        return repo.findAll();
    }

    public List<Catalogo> listarAtivos() {
        return repo.findByStatus((short)1);
    }

    public Catalogo buscarPorId(Integer idProfissional, Integer idServico) {
        return repo.findById(new CatalogoId(idProfissional, idServico))
                   .orElseThrow(() -> new IllegalArgumentException(
                       "Catálogo não encontrado: " + idProfissional + ", " + idServico
                   ));
    }

    @Transactional
    public Catalogo criar(Catalogo catalogo) {
        catalogo.setStatus((short)1);
        return repo.save(catalogo);
    }

    @Transactional
    public Catalogo atualizar(Integer idProf, Integer idServ, Catalogo dados) {
        Catalogo existente = buscarPorId(idProf, idServ);
        existente.setValor(dados.getValor());
        existente.setTempoMedio(dados.getTempoMedio());
        return repo.save(existente);
    }

    @Transactional
    public void inativar(Integer idProf, Integer idServ) {
        Catalogo existente = buscarPorId(idProf, idServ);
        existente.setStatus((short)2);
        repo.save(existente);
    }
}
