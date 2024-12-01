package com.teste.processamento.pagamento.api.processamento_pagamento_api.infrastructure.persistence.repositories;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities.Pagamento;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.interfaces.repositories.useCases.IPagamentoRepository;

@Repository
public class PagamentoRepositoryImpl implements PagamentoRespository  {

    private Map<Long, Pagamento> bancoDeDados = new HashMap<>();

    @Override
    public Long findById(Long id) {
       
        return id;
    }

    @Override
    public Pagamento save(Pagamento pagamento) {
        bancoDeDados.put(pagamento.getId(), pagamento);
        return pagamento;
    }
    
}
