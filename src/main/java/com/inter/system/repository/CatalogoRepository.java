package com.inter.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inter.system.model.Catalogo;
import com.inter.system.model.CatalogoId;

public interface CatalogoRepository 
  extends JpaRepository<Catalogo, CatalogoId> {
}
