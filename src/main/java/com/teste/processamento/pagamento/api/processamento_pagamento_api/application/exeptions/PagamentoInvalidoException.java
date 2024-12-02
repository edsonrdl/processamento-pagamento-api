package com.teste.processamento.pagamento.api.processamento_pagamento_api.application.exeptions;


public class  PagamentoInvalidoException extends RuntimeException  {
    public PagamentoInvalidoException(String message) {
        super(message);
    }
}