package com.inter.system.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "Catalogo")
public class Catalogo {

    @EmbeddedId
    private CatalogoId id;

    @MapsId("idProfissional")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idProfissional", nullable = false)
    @JsonBackReference("profissional-catalogos")
    private Profissional profissional;

    @MapsId("idServico")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idServico", nullable = false)
    @JsonManagedReference("servico-catalogos")
    private Servico servico;

    @DecimalMin("0.01")
    @Column
    private java.math.BigDecimal valor;

    @Min(1)
    @Column(name = "tempoMedio")
    private Integer tempoMedio;

    @NotNull
    @Min(1) @Max(2)
    @Column(nullable = false)
    private Short status;

    public CatalogoId getId() { return id; }
    public void setId(CatalogoId id) { this.id = id; }

    public Profissional getProfissional() { return profissional; }
    public void setProfissional(Profissional profissional) { this.profissional = profissional; }

    public Servico getServico() { return servico; }
    public void setServico(Servico servico) { this.servico = servico; }

    public java.math.BigDecimal getValor() { return valor; }
    public void setValor(java.math.BigDecimal valor) { this.valor = valor; }

    public Integer getTempoMedio() { return tempoMedio; }
    public void setTempoMedio(Integer tempoMedio) { this.tempoMedio = tempoMedio; }

    public Short getStatus() { return status; }
    public void setStatus(Short status) { this.status = status; }
}
