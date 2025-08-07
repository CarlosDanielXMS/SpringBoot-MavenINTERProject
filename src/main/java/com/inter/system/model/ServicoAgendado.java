package com.inter.system.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "id"
)
@Entity
@Table(name = "Servico_Agendado")
public class ServicoAgendado {

    @EmbeddedId
    private ServicoAgendadoId id;

    @MapsId("idAgenda")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idAgenda", nullable = false)
    private Agenda agenda;

    @MapsId("idServico")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idServico", nullable = false)
    private Servico servico;

    @MapsId("idProfissional")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idProfissional", nullable = false)
    private Profissional profissional;

    @NotNull
    @DecimalMin("0.00")
    @Column(nullable = false)
    private BigDecimal valor;

    @NotNull
    @Min(1) @Max(2)
    @Column(nullable = false)
    private Short status;

    public ServicoAgendadoId getId() { return id; }
    public void setId(ServicoAgendadoId id) { this.id = id; }

    public Agenda getAgenda() { return agenda; }
    public void setAgenda(Agenda agenda) { this.agenda = agenda; }

    public Servico getServico() { return servico; }
    public void setServico(Servico servico) { this.servico = servico; }

    public Profissional getProfissional() { return profissional; }
    public void setProfissional(Profissional profissional) { this.profissional = profissional; }

    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }

    public Short getStatus() { return status; }
    public void setStatus(Short status) { this.status = status; }
}
