package com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities.pagamentoState;

import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities.Pagamento;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.interfaces.repositories.useCases.IEstadoPagameto;

public class EstadoPendente implements IEstadoPagameto {

    @Override
    public void processar(Pagamento pagamento){
        System.out.println("Processando pagamento...");
        pagamento.setEstado(new EstadoProcessadoComSucesso());
    }

    @Override
    public void cancelar(Pagamento pagamento) {
        System.out.println("Pagamento pendente cancelado...");
        pagamento.setEstado(new EstadoProcessadoComFalha());
    }

    @Override
    public void concluir(Pagamento pagamento) {
     throw new UnsupportedOperationException("Não é possível concluir um pagamento pendente");
    }
}
