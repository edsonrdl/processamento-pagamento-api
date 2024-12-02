package com.teste.processamento.pagamento.api.processamento_pagamento_api.exepctions;

import com.teste.processamento.pagamento.api.processamento_pagamento_api.application.exeptions.EstadoInvalidoException;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.application.exeptions.PagamentoInvalidoException;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.application.exeptions.PagamentoNaoEncontradoException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PagamentoNaoEncontradoException.class)
    public ResponseEntity<Map<String, String>> handlePagamentoNaoEncontrado(PagamentoNaoEncontradoException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("erro", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PagamentoInvalidoException.class)
    public ResponseEntity<Map<String, String>> handlePagamentoInvalido(PagamentoInvalidoException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("erro", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EstadoInvalidoException.class)
    public ResponseEntity<Map<String, String>> handleEstadoInvalido(EstadoInvalidoException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("erro", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
        Map<String, String> response = new HashMap<>();
        response.put("erro", "Erro inesperado: " + ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}