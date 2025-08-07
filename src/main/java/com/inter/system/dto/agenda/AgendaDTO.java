package com.inter.system.dto.agenda;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AgendaDTO {
    private Long id;
    private Long clienteId;
    private String clienteNome;
    private LocalDateTime dataHora;
    private Integer tempoTotal;
    private BigDecimal valorTotal;
    private Short status;

    public AgendaDTO() {
    }

    public AgendaDTO(Long id, Long clienteId, String clienteNome, LocalDateTime dataHora, Integer tempoTotal,
            BigDecimal valorTotal, Short status) {
        this.id = id;
        this.clienteId = clienteId;
        this.clienteNome = clienteNome;
        this.dataHora = dataHora;
        this.tempoTotal = tempoTotal;
        this.valorTotal = valorTotal;
        this.status = status;
    }
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getClienteId() {
        return clienteId;
    }
    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }
    public String getClienteNome() {
        return clienteNome;
    }
    public void setClienteNome(String clienteNome) {
        this.clienteNome = clienteNome;
    }
    public LocalDateTime getDataHora() {
        return dataHora;
    }
    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }
    public Integer getTempoTotal() {
        return tempoTotal;
    }
    public void setTempoTotal(Integer tempoTotal) {
        this.tempoTotal = tempoTotal;
    }
    public BigDecimal getValorTotal() {
        return valorTotal;
    }
    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }
    public Short getStatus() {
        return status;
    }
    public void setStatus(Short status) {
        this.status = status;
    }

}
