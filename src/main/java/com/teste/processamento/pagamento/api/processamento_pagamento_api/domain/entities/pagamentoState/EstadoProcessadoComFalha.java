package com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities.pagamentoState;

import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities.Pagamento;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities.enuns.StatusPagamento;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.interfaces.repositories.services.IEstadoPagamento;

public class EstadoProcessadoComFalha implements IEstadoPagamento {

    @Override
    public void alterarStatus(Pagamento pagamento, StatusPagamento novoStatus) {
        if (novoStatus == StatusPagamento.PENDENTE_PROCESSAMENTO) {
            pagamento.setEstado(new EstadoPendente());
            pagamento.setStatus(StatusPagamento.PENDENTE_PROCESSAMENTO);
        } else {
            throw new UnsupportedOperationException(
                "Somente pagamentos pendentes podem ser alterados para este status."
            );
        }
    }

    @Override
    public void processar(Pagamento pagamento) {
        throw new UnsupportedOperationException("Pagamento com falha não pode ser processado diretamente.");
    }

    @Override
    public void cancelar(Pagamento pagamento) {
        throw new UnsupportedOperationException("Não é possível cancelar um pagamento processado com falha.");
    }

    @Override
    public void reprocessar(Pagamento pagamento) {
        alterarStatus(pagamento, StatusPagamento.PENDENTE_PROCESSAMENTO);
    }
}
