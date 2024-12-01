package com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities.pagamentoState;

import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities.Pagamento;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.interfaces.repositories.services.IEstadoPagamento;

public class EstadoProcessadoComFalha implements IEstadoPagamento {

    @Override
    public void processar(Pagamento pagamento) {
        throw new UnsupportedOperationException("Pagamento já foi processado com sucesso.");
    }

    @Override
    public void cancelar(Pagamento pagamento) {
        throw new UnsupportedOperationException("Não é possível cancelar um pagamento processado com sucesso.");
    }

    @Override
    public void reprocessar(Pagamento pagamento) {
        throw new UnsupportedOperationException("Pagamento processado com sucesso não pode ser reprocessado.");
    }

    @Override
    public void concluir(Pagamento pagamento) {
        System.out.println("Pagamento concluído com sucesso.");
    }
}
