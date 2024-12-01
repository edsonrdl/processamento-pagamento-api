package com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.interfaces.repositories.services;

import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities.Pagamento;

public interface IEstadoPagamento {
    void processar(Pagamento pagamento);
    void cancelar(Pagamento pagamento);
    void reprocessar(Pagamento pagamento);
    void concluir(Pagamento pagamento);
}
