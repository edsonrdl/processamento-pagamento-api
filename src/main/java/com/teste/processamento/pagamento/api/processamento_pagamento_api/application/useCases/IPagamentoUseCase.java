package com.teste.processamento.pagamento.api.processamento_pagamento_api.application.useCases;

import java.util.List;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities.Pagamento;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.infrastructure.model.PagamentoModel;

public interface IPagamentoUseCase {
    PagamentoModel create(Pagamento pagamento); 
    PagamentoModel findById(Long id); 
    List<PagamentoModel> findAll(); 
    PagamentoModel update(Pagamento pagamento, Long id); 
    boolean delete(Long id); 
}
