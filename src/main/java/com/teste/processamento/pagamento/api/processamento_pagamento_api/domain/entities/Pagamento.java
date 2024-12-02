package com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities;

import java.math.BigDecimal;

import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities.enuns.MetodoPagamento;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities.enuns.StatusPagamento;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities.pagamentoState.EstadoPendente;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.interfaces.repositories.services.IEstadoPagamento;

public class Pagamento {
    private Long id;
    private int codigoDebito;
    private int identificadorPagador;
    private MetodoPagamento metodo;
    private String numeroCartao;
    private BigDecimal valor;
    private IEstadoPagamento estado;
    private StatusPagamento status;

    public Pagamento(int codigoDebito, int identificadorPagador, MetodoPagamento metodo, String numeroCartao, BigDecimal valor) {
        this.codigoDebito = codigoDebito;
        this.identificadorPagador = identificadorPagador;
        this.metodo = metodo;
        this.numeroCartao = numeroCartao;
        this.valor = valor;
        this.estado = new EstadoPendente(); 
        this.status = StatusPagamento.PENDENTE_PROCESSAMENTO; 
    }

    public void processar() {
        estado.processar(this);
    }

    public void cancelar() {
        estado.cancelar(this);
    }

    public void reprocessar() {
        estado.reprocessar(this);
    }

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

    public int getIdentificadorPagador() {
        return identificadorPagador;
    }

    public void setIdentificadorPagador(int identificadorPagador) {
        this.identificadorPagador = identificadorPagador;
    }

    public MetodoPagamento getMetodo() {
        return metodo;
    }

    public void setMetodo(MetodoPagamento metodo) {
        this.metodo = metodo;
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

    public IEstadoPagamento getEstado() {
        return estado;
    }

    public void setEstado(IEstadoPagamento estado) {
        this.estado = estado;
    }

    public StatusPagamento getStatus() {
        return status;
    }

    public void setStatus(StatusPagamento status) {
        this.status = status;
    }
}
