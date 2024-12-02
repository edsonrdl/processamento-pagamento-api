package com.teste.processamento.pagamento.api.processamento_pagamento_api.application.exeptions;

public class PagamentoNaoEncontradoException extends RuntimeException {
    public PagamentoNaoEncontradoException(String message) {
        super(message);
    }
}
