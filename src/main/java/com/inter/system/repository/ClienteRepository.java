package com.inter.system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inter.system.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    List<Cliente> findByStatus(Short status);
}
