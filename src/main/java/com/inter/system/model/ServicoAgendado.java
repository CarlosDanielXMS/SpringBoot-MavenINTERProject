package com.inter.system.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "Servico_Agendado")
public class ServicoAgendado {

    @EmbeddedId
    private ServicoAgendadoId id;

    @MapsId("idAgenda")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idAgenda", nullable = false)
    @JsonBackReference("agenda-servicosAgendados")
    private Agenda agenda;

    @MapsId("idServico")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idServico", nullable = false)
    @JsonManagedReference("servico-servicosAgendados")
    private Servico servico;

    @MapsId("idProfissional")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idProfissional", nullable = false)
    @JsonManagedReference("profissional-servicosAgendados")
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
