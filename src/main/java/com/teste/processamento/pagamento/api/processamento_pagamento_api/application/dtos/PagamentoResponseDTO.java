package com.teste.processamento.pagamento.api.processamento_pagamento_api.application.dtos;

import java.math.BigDecimal;

import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities.enuns.MetodoPagamento;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities.enuns.StatusPagamento;

public class PagamentoResponseDTO {
    private Long id;
    private int codigoDebito;
    private String identificadorPagador;
    private MetodoPagamento metodoPagamento;
    private String numeroCartao; 
    private BigDecimal valor;
    private StatusPagamento status;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCodigoDebito() {
        return codigoDebito;
    }

    public void setCodigoDebito(int codigoDebito) {
        this.codigoDebito = codigoDebito;
    }

    public String getIdentificadorPagador() {
        return identificadorPagador;
    }

    public void setIdentificadorPagador(String identificadorPagador) {
        this.identificadorPagador = identificadorPagador;
    }

    public MetodoPagamento getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(MetodoPagamento metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public StatusPagamento getStatus() {
        return status;
    }

    public void setStatus(StatusPagamento status) {
        this.status = status;
    }
}