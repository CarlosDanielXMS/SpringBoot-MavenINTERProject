package com.inter.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inter.system.model.Agenda;

public interface AgendaRepository extends JpaRepository<Agenda, Integer> {
}
