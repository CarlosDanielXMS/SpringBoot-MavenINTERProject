package com.inter.system.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "Profissional")
public class Profissional extends Usuario {

    @NotNull
    @DecimalMin("0.01")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal salarioFixo;

    @NotNull
    @DecimalMin("0.00")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal comissao;

    @OneToMany(mappedBy = "profissional", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServicoAgendado> servicosAgendados = new ArrayList<>();

    @OneToMany(mappedBy = "profissional", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Catalogo> catalogos = new ArrayList<>();
    
    public void setSalarioFixo(BigDecimal salarioFixo) { this.salarioFixo = salarioFixo; }
    public void setComissao(BigDecimal comissao) { this.comissao = comissao; }
    public void setServicosAgendados(List<ServicoAgendado> servicosAgendados) { this.servicosAgendados = servicosAgendados; }
    public void setCatalogos(List<Catalogo> catalogos) { this.catalogos = catalogos; }
    
    public BigDecimal getSalarioFixo() { return salarioFixo; }
    public BigDecimal getComissao() { return comissao; }
    public List<ServicoAgendado> getServicosAgendados() { return servicosAgendados; }
    public List<Catalogo> getCatalogos() { return catalogos; }

}
