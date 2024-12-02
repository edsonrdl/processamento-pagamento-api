package com.teste.processamento.pagamento.api.processamento_pagamento_api.application.dtos;

import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities.enuns.StatusPagamento;

public class PagamentoUpdateRequestDTO {
    private Long codigoDebito;
    private StatusPagamento status;

    public Long getCodigoDebito() {
        return codigoDebito;
    }

    public void setCodigoDebito(Long codigoDebito) {
        this.codigoDebito = codigoDebito;
    }

    public StatusPagamento getStatus() {
        return status;
    }

    public void setStatus(StatusPagamento status) {
        this.status = status;
    }
}
