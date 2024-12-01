package com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities;

import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities.pagamentoState.EstadoPendente;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.interfaces.repositories.useCases.IEstadoPagameto;

public class Pagamento {
    private Long id;
    private double valor;
    private IEstadoPagameto estado;

    public Pagamento(long id, double valor, IEstadoPagameto estado) {
        this.id = id;
        this.valor = valor;
        this.estado = new EstadoPendente();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public IEstadoPagameto getEstado() {
        return estado;
    }

    public void setEstado(IEstadoPagameto estado) {
        this.estado = estado;
    }

    public void processar() {
        estado.processar(this);
    }

    public void cancelar() {
        estado.cancelar(this);
    }

    public void concluir() {
        estado.concluir(this);
    }

}
