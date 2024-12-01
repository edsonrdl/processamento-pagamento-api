package com.teste.processamento.pagamento.api.processamento_pagamento_api.infrastructure.service;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities.Pagamento;


public interface IPagamentoService {

    public Pagamento receberPagamento(Long id);

    public Pagamento cancelarPagamento(Long id);

    public Pagamento concluirPagamento(Long id) ;

}
