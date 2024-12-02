package com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.interfaces.repositories.services;

import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities.Pagamento;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities.enuns.StatusPagamento;

public interface IEstadoPagamento {
    void alterarStatus(Pagamento pagamento, StatusPagamento novoStatus);

    void processar(Pagamento pagamento);

    void cancelar(Pagamento pagamento);

    void reprocessar(Pagamento pagamento);
}
