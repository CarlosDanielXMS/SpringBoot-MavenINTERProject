package com.inter.system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inter.system.model.Servico;

public interface ServicoRepository extends JpaRepository<Servico, Integer> {
    List<Servico> findByStatus(Short status);
}
