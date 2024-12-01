package com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities;
import java.math.BigDecimal;

import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities.enuns.MetodoPagamento;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities.pagamentoState.EstadoPendente;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.interfaces.repositories.services.IEstadoPagamento;

public class Pagamento {
    private Long id;
    private int codigoDebito;
    private String identificadorPagador;
    private MetodoPagamento metodo;
    private String numeroCartao;
    private BigDecimal valor;
    private IEstadoPagamento estado;

    public Pagamento(int codigoDebito, String identificadorPagador, MetodoPagamento metodo, String numeroCartao, BigDecimal valor) {
        this.codigoDebito = codigoDebito;
        this.identificadorPagador = identificadorPagador;
        this.metodo = metodo;
        this.numeroCartao = numeroCartao;
        this.valor = valor;
        this.estado = new EstadoPendente();
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

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return int return the codigoDebito
     */
    public int getCodigoDebito() {
        return codigoDebito;
    }

    /**
     * @param codigoDebito the codigoDebito to set
     */
    public void setCodigoDebito(int codigoDebito) {
        this.codigoDebito = codigoDebito;
    }

    /**
     * @return String return the identificadorPagador
     */
    public String getIdentificadorPagador() {
        return identificadorPagador;
    }

    /**
     * @param identificadorPagador the identificadorPagador to set
     */
    public void setIdentificadorPagador(String identificadorPagador) {
        this.identificadorPagador = identificadorPagador;
    }

    /**
     * @return MetodoPagamento return the metodo
     */
    public MetodoPagamento getMetodo() {
        return metodo;
    }

    /**
     * @param metodo the metodo to set
     */
    public void setMetodo(MetodoPagamento metodo) {
        this.metodo = metodo;
    }

    /**
     * @return String return the numeroCartao
     */
    public String getNumeroCartao() {
        return numeroCartao;
    }

    /**
     * @param numeroCartao the numeroCartao to set
     */
    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    /**
     * @return BigDecimal return the valor
     */
    public BigDecimal getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    /**
     * @return IEstadoPagameto return the estado
     */
    public IEstadoPagamento getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(IEstadoPagamento estado) {
        this.estado = estado;
    }

}
