package com.teste.processamento.pagamento.api.processamento_pagamento_api.application.exeptions;

public class EstadoInvalidoException extends RuntimeException {
    public EstadoInvalidoException(String message) {
        super(message);
    }
}