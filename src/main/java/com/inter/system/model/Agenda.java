package com.inter.system.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "Agenda")
public class Agenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "idCliente")
    private Cliente cliente;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime dataHora;

    @NotNull
    @Min(0)
    @Column(nullable = false)
    private Integer tempoTotal;

    @NotNull
    @DecimalMin("0.00")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valorTotal;

    @NotNull
    @Min(1)
    @Max(3)
    @Column(nullable = false)
    private Short status;

    @OneToMany(mappedBy = "agenda", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServicoAgendado> servicos = new ArrayList<>();
    
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
    public void setTempoTotal(Integer tempoTotal) { this.tempoTotal = tempoTotal; }
    public void setValorTotal(BigDecimal valorTotal) { this.valorTotal = valorTotal; }
    public void setStatus(Short status) { this.status = status; }

    public Integer getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public LocalDateTime getDataHora() { return dataHora; }
    public Integer getTempoTotal() { return tempoTotal; }
    public BigDecimal getValorTotal() { return valorTotal; }
    public Short getStatus() { return status; }
    public List<ServicoAgendado> getServicos() { return servicos; }
}
