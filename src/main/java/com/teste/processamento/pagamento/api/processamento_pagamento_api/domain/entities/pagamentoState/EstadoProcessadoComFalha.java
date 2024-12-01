package com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities.pagamentoState;

import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities.Pagamento;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.interfaces.repositories.services.IEstadoPagamento;

public class EstadoProcessadoComFalha implements IEstadoPagamento {

    @Override
    public void processar(Pagamento pagamento) {
        throw new UnsupportedOperationException("Pagamento com falha não pode ser processado novamente diretamente.");
    }

    @Override
    public void cancelar(Pagamento pagamento) {
        System.out.println("Pagamento com falha cancelado.");
        pagamento.setEstado(new EstadoPendente());
    }

    @Override
    public void reprocessar(Pagamento pagamento) {
        System.out.println("Reprocessando pagamento com falha...");
        pagamento.setEstado(new EstadoPendente());
    }

    @Override
    public void concluir(Pagamento pagamento) {
        throw new UnsupportedOperationException("Pagamento com falha não pode ser concluído.");
    }
}
