package com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities.pagamentoState;

import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities.Pagamento;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.interfaces.repositories.useCases.IEstadoPagameto;

public class EstadoProcessadoComSucesso  implements IEstadoPagameto {

    @Override
    public void processar(Pagamento pagamento) {
        throw new UnsupportedOperationException("Pagamento já processado com sucesso.");
    }

    @Override
    public void cancelar(Pagamento pagamento) {
        throw new UnsupportedOperationException("Não é possível cancelar um pagamento processado com sucesso.");
    }

    @Override
    public void concluir(Pagamento pagamento) {
        System.out.println("Pagamento concluído com sucesso.");
    }

}
