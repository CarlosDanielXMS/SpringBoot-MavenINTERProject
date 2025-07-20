package com.inter.system.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "Catalogo")
@IdClass(CatalogoId.class)
public class Catalogo {

    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "idProfissional", referencedColumnName = "id")
    private Profissional profissional;

    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "idServico", referencedColumnName = "id")
    private Servico servico;

    @NotNull
    @DecimalMin("0.01")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;

    @NotNull
    @Min(1)
    @Column(nullable = false)
    private Integer tempoMedio;

    @NotNull
    @Min(1)
    @Max(2)
    @Column(nullable = false)
    private Short status;

    public void setValor(BigDecimal valor) { this.valor = valor; }
    public void setTempoMedio(Integer tempoMedio) { this.tempoMedio = tempoMedio; }
    public void setStatus(Short status) { this.status = status; }

    public Profissional getProfissional() { return profissional; }
    public Servico getServico() { return servico; }
    public BigDecimal getValor() { return valor; }
    public Integer getTempoMedio() { return tempoMedio; }
    public Short getStatus() { return status; }
}
