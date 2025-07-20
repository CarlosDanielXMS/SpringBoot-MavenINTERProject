// src/main/java/com/inter/system/model/Profissional.java
package com.inter.system.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "Profissional")
public class Profissional extends Usuario {

    @NotNull
    @Column(name = "salarioFixo", nullable = false)
    private BigDecimal salarioFixo;

    @NotNull
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal comissao;

    @OneToMany(mappedBy = "profissional", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServicoAgendado> servicosAgendados;

    @OneToMany(mappedBy = "profissional", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Catalogo> catalogos;

    // getters & setters
    public BigDecimal getSalarioFixo() { return salarioFixo; }
    public void setSalarioFixo(BigDecimal salarioFixo) { this.salarioFixo = salarioFixo; }

    public BigDecimal getComissao() { return comissao; }
    public void setComissao(BigDecimal comissao) { this.comissao = comissao; }

    public List<ServicoAgendado> getServicosAgendados() { return servicosAgendados; }
    public void setServicosAgendados(List<ServicoAgendado> servicosAgendados) {
        this.servicosAgendados = servicosAgendados;
    }

    public List<Catalogo> getCatalogos() { return catalogos; }
    public void setCatalogos(List<Catalogo> catalogos) {
        this.catalogos = catalogos;
    }
}
