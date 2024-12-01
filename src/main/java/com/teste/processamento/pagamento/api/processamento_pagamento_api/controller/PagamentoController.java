package com.teste.processamento.pagamento.api.processamento_pagamento_api.controller;

import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities.Pagamento;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities.enuns.StatusPagamento;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.infrastructure.model.PagamentoModel;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.infrastructure.mapper.PagamentoMapperModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    private final IPagamentoService pagamentoService;
    
    private final PagamentoMapperModel mapper;

    @Autowired
    public PagamentoController(IPagamentoService pagamentoService, PagamentoMapperModel mapper) {
        this.pagamentoService = pagamentoService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<Pagamento> createPagamento(@RequestBody Pagamento pagamento) {
        Pagamento createdPagamento = pagamentoService.createPagamento(pagamento);
        return new ResponseEntity<>(createdPagamento, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pagamento> getPagamentoById(@PathVariable Long id) {
        try {
            Pagamento pagamento = pagamentoService.findPagamentoById(id);
            return new ResponseEntity<>(pagamento, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<Pagamento>> getAllPagamentos() {
        List<PagamentoModel> pagamentos = pagamentoService.findAllPagamentos();
        List<Pagamento> pagamentosMapped = pagamentos.stream()
                .map(mapper::toPagamento)
                .collect(Collectors.toList());
        return new ResponseEntity<>(pagamentosMapped, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pagamento> updatePagamento(@PathVariable Long id, @RequestBody Pagamento pagamento) {
        try {
            Pagamento updatedPagamento = pagamentoService.updatePagamento(id, pagamento);
            return new ResponseEntity<>(updatedPagamento, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePagamento(@PathVariable Long id) {
        try {
            pagamentoService.deletePagamento(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}/processar")
    public ResponseEntity<Pagamento> processarPagamento(@PathVariable Long id) {
        try {
            Pagamento pagamento = pagamentoService.processarPagamento(id);
            return new ResponseEntity<>(pagamento, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}/cancelar")
    public ResponseEntity<Pagamento> cancelarPagamento(@PathVariable Long id) {
        try {
            Pagamento pagamento = pagamentoService.cancelarPagamento(id);
            return new ResponseEntity<>(pagamento, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}/concluir")
    public ResponseEntity<Pagamento> concluirPagamento(@PathVariable Long id) {
        try {
            Pagamento pagamento = pagamentoService.concluirPagamento(id);
            return new ResponseEntity<>(pagamento, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
