package com.inter.system.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "Profissional")
public class Profissional extends Usuario {

    @NotNull
    @Column(name = "salarioFixo", nullable = false)
    private BigDecimal salarioFixo;

    @NotNull
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal comissao;

    @OneToMany(mappedBy = "profissional", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference("profissional-servicosAgendados")
    private List<ServicoAgendado> servicosAgendados;

    @OneToMany(mappedBy = "profissional", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("profissional-catalogos")
    private List<Catalogo> catalogos;

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
