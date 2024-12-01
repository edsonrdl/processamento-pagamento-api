package com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities.pagamentoState;

import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities.Pagamento;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities.enuns.StatusPagamento;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.interfaces.repositories.services.IEstadoPagamento;

public class EstadoProcessadoComSucesso implements IEstadoPagamento {

   @Override
    public void processar(Pagamento pagamento) {
        throw new UnsupportedOperationException("Pagamento com falha não pode ser processado diretamente.");
    }

    @Override
    public void cancelar(Pagamento pagamento) {
        System.out.println("Cancelando pagamento com falha...");
        pagamento.setEstado(new EstadoPendente());
        pagamento.setStatus(StatusPagamento.PENDENTE_PROCESSAMENTO);
    }

    @Override
    public void reprocessar(Pagamento pagamento) {
        System.out.println("Reprocessando pagamento...");
        pagamento.setEstado(new EstadoPendente());
        pagamento.setStatus(StatusPagamento.PENDENTE_PROCESSAMENTO);
    }

    @Override
    public void concluir(Pagamento pagamento) {
        throw new UnsupportedOperationException("Pagamento com falha não pode ser concluído.");
    }
}
