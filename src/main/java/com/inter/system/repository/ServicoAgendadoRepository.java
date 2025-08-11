package com.inter.system.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.inter.system.model.ServicoAgendado;
import com.inter.system.model.ServicoAgendadoId;

public interface ServicoAgendadoRepository 
  extends JpaRepository<ServicoAgendado, ServicoAgendadoId> {
    @Query("""
           select sa
           from ServicoAgendado sa
             join sa.agenda a
           where sa.profissional.id = :profId
             and a.status = 1
             and a.dataHora between :inicio and :fim
           """)
    List<ServicoAgendado> findDoProfissionalNoPeriodo(
            @Param("profId") Integer profissionalId,
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim);
}
