package com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities.pagamentoState;

import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities.Pagamento;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.interfaces.repositories.useCases.IEstadoPagameto;

public class EstadoProcessadoComFalha implements IEstadoPagameto {

    @Override
    public void processar(Pagamento pagamento) {
        throw new UnsupportedOperationException("Pagamento com falha não pode ser reprocessado.");
    }

    @Override
    public void cancelar(Pagamento pagamento) {
        System.out.println("Pagamento com falha cancelado.");
        pagamento.setEstado(new EstadoPendente()); 
    }

    @Override
    public void concluir(Pagamento pagamento) {
        throw new UnsupportedOperationException("Pagamento com falha não pode ser concluído.");
    }

}
