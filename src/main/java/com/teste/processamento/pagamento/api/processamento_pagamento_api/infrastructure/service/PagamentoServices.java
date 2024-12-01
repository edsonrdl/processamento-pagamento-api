package com.teste.processamento.pagamento.api.processamento_pagamento_api.infrastructure.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities.Pagamento;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.interfaces.repositories.useCases.IPagamentoRepository;

@Service
public class PagamentoServices implements IPagamentoService {

    @Autowired
    private IPagamentoRepository pagamentoRepository;

    public Pagamento receberPagamento(Long id) {
        Pagamento pagamento = pagamentoRepository.findById(id)
                .orElseThrow(() -> new PagamentoNaoEncontradoException("Pagamento não encontrado"));

        pagamento.processar();
        pagamentoRepository.save(pagamento);
        return pagamento;
    }

    public Pagamento cancelarPagamento(Long id) {
        Pagamento pagamento = pagamentoRepository.findById(id)
                .orElseThrow(() -> new PagamentoNaoEncontradoException("Pagamento não encontrado"));

        pagamento.cancelar();
        pagamentoRepository.save(pagamento);
        return pagamento;
    }

    public Pagamento concluirPagamento(Long id) {
        Pagamento pagamento = pagamentoRepository.findById(id)
                .orElseThrow(() -> new PagamentoNaoEncontradoException("Pagamento não encontrado"));

        pagamento.concluir();
        pagamentoRepository.save(pagamento);
        return pagamento;
    }

}
