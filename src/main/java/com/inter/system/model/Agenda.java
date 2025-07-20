// src/main/java/com/inter/system/model/Agenda.java
package com.inter.system.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "Agenda")
public class Agenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCliente", nullable = false)
    private Cliente cliente;

    @NotNull
    @Column(name = "dataHora", nullable = false)
    private LocalDateTime dataHora;

    @NotNull
    @Min(0)
    @Column(name = "tempoTotal", nullable = false)
    private Integer tempoTotal;

    @NotNull
    @DecimalMin("0.00")
    @Column(name = "valorTotal", nullable = false)
    private BigDecimal valorTotal;

    @NotNull
    @Min(1) @Max(3)
    @Column(nullable = false)
    private Short status;

    @OneToMany(mappedBy = "agenda", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServicoAgendado> servicosAgendados;

    // getters & setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }

    public Integer getTempoTotal() { return tempoTotal; }
    public void setTempoTotal(Integer tempoTotal) { this.tempoTotal = tempoTotal; }

    public BigDecimal getValorTotal() { return valorTotal; }
    public void setValorTotal(BigDecimal valorTotal) { this.valorTotal = valorTotal; }

    public Short getStatus() { return status; }
    public void setStatus(Short status) { this.status = status; }

    public List<ServicoAgendado> getServicosAgendados() { return servicosAgendados; }
    public void setServicosAgendados(List<ServicoAgendado> servicosAgendados) {
        this.servicosAgendados = servicosAgendados;
    }
}
