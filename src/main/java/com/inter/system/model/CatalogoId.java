package com.inter.system.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CatalogoId implements Serializable {

    private Integer idProfissional;
    private Integer idServico;

    public CatalogoId() {}

    public CatalogoId(Integer idProfissional, Integer idServico) {
        this.idProfissional = idProfissional;
        this.idServico = idServico;
    }

    public Integer getIdProfissional() { return idProfissional; }
    public void setIdProfissional(Integer idProfissional) { this.idProfissional = idProfissional; }

    public Integer getIdServico() { return idServico; }
    public void setIdServico(Integer idServico) { this.idServico = idServico; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CatalogoId)) return false;
        CatalogoId that = (CatalogoId) o;
        return Objects.equals(idProfissional, that.idProfissional)
            && Objects.equals(idServico, that.idServico);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProfissional, idServico);
    }
}
