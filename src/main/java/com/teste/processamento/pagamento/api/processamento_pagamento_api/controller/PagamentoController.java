package com.teste.processamento.pagamento.api.processamento_pagamento_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities.Pagamento;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.infrastructure.service.IPagamentoService;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {
    
    @Autowired
    private IPagamentoService pagamentoService;

    @PostMapping("/{id}/processar")
    public Pagamento processarPagamento(@PathVariable Long id) {
        return pagamentoService.receberPagamento(id);
    }

    @PostMapping("/{id}/cancelar")
    public Pagamento cancelarPagamento(@PathVariable Long id) {
        return pagamentoService.cancelarPagamento(id);
    }

    @PostMapping("/{id}/concluir")
    public Pagamento concluirPagamento(@PathVariable Long id) {
        return pagamentoService.concluirPagamento(id);
    }
    
}
