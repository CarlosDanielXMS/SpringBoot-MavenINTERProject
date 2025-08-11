package com.inter.system.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inter.system.model.Agenda;

public interface AgendaRepository extends JpaRepository<Agenda, Integer> {

    List<Agenda> findByStatus(Short status);

    List<Agenda> findByDataHoraBetween(LocalDateTime inicio, LocalDateTime fim);
}
