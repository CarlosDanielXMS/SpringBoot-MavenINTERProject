// src/main/java/com/inter/system/model/Servico.java
package com.inter.system.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "Servico")
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Column(nullable = false, length = 50, unique = true)
    private String descricao;

    @NotNull
    @DecimalMin("0.01")
    @Column(nullable = false)
    private BigDecimal valor;

    @NotNull
    @Min(1)
    @Column(name = "tempoMedio", nullable = false)
    private Integer tempoMedio;

    @NotNull
    @Min(1) @Max(2)
    @Column(nullable = false)
    private Short status;

    @OneToMany(mappedBy = "servico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServicoAgendado> servicosAgendados;

    @OneToMany(mappedBy = "servico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Catalogo> catalogos;

    // getters & setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }

    public Integer getTempoMedio() { return tempoMedio; }
    public void setTempoMedio(Integer tempoMedio) { this.tempoMedio = tempoMedio; }

    public Short getStatus() { return status; }
    public void setStatus(Short status) { this.status = status; }

    public List<ServicoAgendado> getServicosAgendados() { return servicosAgendados; }
    public void setServicosAgendados(List<ServicoAgendado> servicosAgendados) {
        this.servicosAgendados = servicosAgendados;
    }

    public List<Catalogo> getCatalogos() { return catalogos; }
    public void setCatalogos(List<Catalogo> catalogos) {
        this.catalogos = catalogos;
    }
}
