package com.inter.system.model;

import java.io.Serializable;
import java.util.Objects;

public class ServicoAgendadoId implements Serializable {

    private Integer agenda;
    private Integer servico;
    private Integer profissional;

    public ServicoAgendadoId() {}

    public ServicoAgendadoId(Integer agenda, Integer servico, Integer profissional) {
        this.agenda = agenda;
        this.servico = servico;
        this.profissional = profissional;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof ServicoAgendadoId)) return false;
        ServicoAgendadoId that = (ServicoAgendadoId) object;
        return Objects.equals(agenda, that.agenda) &&
               Objects.equals(servico, that.servico) &&
               Objects.equals(profissional, that.profissional);
    }

    @Override
    public int hashCode() {
        return Objects.hash(agenda, servico, profissional);
    }
}
