package com.inter.system.model;

import java.io.Serializable;
import java.util.Objects;

public class CatalogoId implements Serializable {

    private Integer profissional;
    private Integer servico;

    public CatalogoId() {}

    public CatalogoId(Integer profissional, Integer servico) {
        this.profissional = profissional;
        this.servico = servico;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CatalogoId)) return false;
        CatalogoId that = (CatalogoId) o;
        return Objects.equals(profissional, that.profissional) &&
               Objects.equals(servico, that.servico);
    }

    @Override
    public int hashCode() {
        return Objects.hash(profissional, servico);
    }
}
