package com.teste.processamento.pagamento.api.processamento_pagamento_api.infrastructure.mapper;

import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities.Pagamento;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.infrastructure.model.PagamentoModel;

public interface IPagamentoMapperModel {

    Pagamento toPagamento(PagamentoModel pagamentoModel);

    PagamentoModel toPagamentoModel(Pagamento pagamento);
}
