package com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities.pagamentoState;

import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities.Pagamento;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities.enuns.StatusPagamento;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.interfaces.repositories.services.IEstadoPagamento;

public class EstadoProcessadoComSucesso implements IEstadoPagamento {

    @Override
    public void alterarStatus(Pagamento pagamento, StatusPagamento novoStatus) {
        throw new UnsupportedOperationException(
            "Pagamentos processados com sucesso não podem ter seu status alterado."
        );
    }

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
}
