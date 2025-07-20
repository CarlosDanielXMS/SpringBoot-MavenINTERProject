package com.inter.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inter.system.model.ServicoAgendado;
import com.inter.system.model.ServicoAgendadoId;

public interface ServicoAgendadoRepository 
  extends JpaRepository<ServicoAgendado, ServicoAgendadoId> {
}
