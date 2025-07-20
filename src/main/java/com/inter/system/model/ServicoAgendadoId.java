// src/main/java/com/inter/system/model/ServicoAgendadoId.java
package com.inter.system.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ServicoAgendadoId implements Serializable {

    private Integer idAgenda;
    private Integer idServico;
    private Integer idProfissional;

    public ServicoAgendadoId() {}

    public ServicoAgendadoId(Integer idAgenda, Integer idServico, Integer idProfissional) {
        this.idAgenda = idAgenda;
        this.idServico = idServico;
        this.idProfissional = idProfissional;
    }

    // getters, setters, equals()/hashCode()
    public Integer getIdAgenda() { return idAgenda; }
    public void setIdAgenda(Integer idAgenda) { this.idAgenda = idAgenda; }

    public Integer getIdServico() { return idServico; }
    public void setIdServico(Integer idServico) { this.idServico = idServico; }

    public Integer getIdProfissional() { return idProfissional; }
    public void setIdProfissional(Integer idProfissional) { this.idProfissional = idProfissional; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ServicoAgendadoId)) return false;
        ServicoAgendadoId that = (ServicoAgendadoId) o;
        return Objects.equals(idAgenda, that.idAgenda)
            && Objects.equals(idServico, that.idServico)
            && Objects.equals(idProfissional, that.idProfissional);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAgenda, idServico, idProfissional);
    }
}
