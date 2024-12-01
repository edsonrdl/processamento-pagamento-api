package com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.interfaces.repositories.useCases;

import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities.Pagamento;

public interface IEstadoPagameto {
    void processar(Pagamento pagamentoEntity);
    void cancelar(Pagamento pagamentoEntity);
    void concluir(Pagamento pagamentoEntity);
}
