package com.teste.processamento.pagamento.api.processamento_pagamento_api.application.useCases;



import java.util.List;
import java.util.Map;

import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities.Pagamento;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.infrastructure.model.PagamentoModel;

public interface IPagamentoUseCase {
    PagamentoModel create(Pagamento pagamento);
    PagamentoModel findById(Long id);
    Map<String, Object> findAll(int page, int size); 
    PagamentoModel update(Pagamento pagamento, Long id);
    boolean delete(Long id);
}
