package com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities.pagamentoState;

import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities.Pagamento;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities.enuns.StatusPagamento;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.interfaces.repositories.services.IEstadoPagamento;

public class EstadoPendente implements IEstadoPagamento {

    @Override
    public void alterarStatus(Pagamento pagamento, StatusPagamento novoStatus) {
        if (novoStatus == StatusPagamento.PROCESSADO_SUCESSO) {
            pagamento.setEstado(new EstadoProcessadoComSucesso());
            pagamento.setStatus(StatusPagamento.PROCESSADO_SUCESSO);
        } else if (novoStatus == StatusPagamento.PROCESSADO_FALHA) {
            pagamento.setEstado(new EstadoProcessadoComFalha());
            pagamento.setStatus(StatusPagamento.PROCESSADO_FALHA);
        } else {
            throw new UnsupportedOperationException("Transição inválida a partir do estado Pendente.");
        }
    }

    @Override
    public void processar(Pagamento pagamento) {
        alterarStatus(pagamento, StatusPagamento.PROCESSADO_SUCESSO);
    }

    @Override
    public void cancelar(Pagamento pagamento) {
        alterarStatus(pagamento, StatusPagamento.PROCESSADO_FALHA);
    }

    @Override
    public void reprocessar(Pagamento pagamento) {
        throw new UnsupportedOperationException("Pagamento pendente não pode ser reprocessado.");
    }
}
