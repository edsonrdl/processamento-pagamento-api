package com.teste.processamento.pagamento.api.processamento_pagamento_api.application.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PagamentoNaoEncontradoException.class)
    public ResponseEntity<?> handlePagamentoNaoEncontrado(PagamentoNaoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErroResponse("Pagamento não encontrado", ex.getMessage()));
    }

    @ExceptionHandler(PagamentoNaoPermitidoException.class)
    public ResponseEntity<?> handlePagamentoNaoPermitido(PagamentoNaoPermitidoException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErroResponse("Pagamento não permitido", ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErroResponse("Erro inesperado", "Algo deu errado."));
    }
}
