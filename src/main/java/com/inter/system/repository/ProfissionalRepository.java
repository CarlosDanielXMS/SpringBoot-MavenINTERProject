package com.inter.system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inter.system.model.Profissional;

public interface ProfissionalRepository extends JpaRepository<Profissional, Integer> {
    List<Profissional> findByStatus(Short status);
}
