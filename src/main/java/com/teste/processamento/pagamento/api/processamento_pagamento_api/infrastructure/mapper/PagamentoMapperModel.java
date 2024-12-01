package com.teste.processamento.pagamento.api.processamento_pagamento_api.infrastructure.mapper;

import java.util.Map;
import java.util.Optional;

import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities.Pagamento;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities.pagamentoState.EstadoPendente;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities.pagamentoState.EstadoProcessadoComFalha;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities.pagamentoState.EstadoProcessadoComSucesso;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.interfaces.repositories.services.IEstadoPagamento;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.infrastructure.model.PagamentoModel;

public class PagamentoMapperModel implements IPagamentoMapperModel {
    @Override
    public PagamentoModel toPagamentoModel(Pagamento pagamento) {
        PagamentoModel pagamentoModel = new PagamentoModel();
        if (pagamento.getId() != null) {
            pagamentoModel.setId(pagamento.getId());
        }
        pagamentoModel.setValor(pagamento.getValor());
        pagamentoModel.setEstado(pagamento.getEstado().getClass().getSimpleName());
        return pagamentoModel;
    }

    @Override
    public Pagamento toPagamento(PagamentoModel pagamentoModel) {

        Map<String, IEstadoPagamento> estadoMap = Map.of(
                "ESTADOPENDENTE", new EstadoPendente(),
                "ESTADOPROCESSADOCOMSUCESSO", new EstadoProcessadoComSucesso(),
                "ESTADOPROCESSADOCOMFALHA", new EstadoProcessadoComFalha());

        IEstadoPagamento estado = Optional.ofNullable(estadoMap.get(pagamentoModel.getEstado().toUpperCase()))
                .orElseThrow(() -> new IllegalArgumentException("Estado inválido: " + pagamentoModel.getEstado()));

        return new Pagamento(pagamentoModel.getId(), pagamentoModel.getValor(), estado);
    }
}
