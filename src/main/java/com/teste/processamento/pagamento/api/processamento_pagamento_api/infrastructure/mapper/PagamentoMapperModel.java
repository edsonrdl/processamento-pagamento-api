package com.teste.processamento.pagamento.api.processamento_pagamento_api.infrastructure.mapper;

import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities.Pagamento;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities.enuns.StatusPagamento;
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

        pagamentoModel.setCodigoDebito(pagamento.getCodigoDebito());
        pagamentoModel.setCpfOuCnpj(pagamento.getIdentificadorPagador());
        pagamentoModel.setMetodoPagamento(pagamento.getMetodo());
        pagamentoModel.setNumeroCartao(pagamento.getNumeroCartao());
        pagamentoModel.setValor(pagamento.getValor());
        pagamentoModel.setStatus(pagamento.getStatus());

        return pagamentoModel;
    }

    @Override
    public Pagamento toPagamento(PagamentoModel pagamentoModel) {
        Pagamento pagamento = new Pagamento(
                pagamentoModel.getCodigoDebito(),
                pagamentoModel.getCpfOuCnpj(),
                pagamentoModel.getMetodoPagamento(),
                pagamentoModel.getNumeroCartao(),
                pagamentoModel.getValor()
        );

        pagamento.setId(pagamentoModel.getId());

    
        IEstadoPagamento estado = mapStatusToEstado(pagamentoModel.getStatus());
        pagamento.setEstado(estado);

       
        pagamento.setStatus(pagamentoModel.getStatus());

        return pagamento;
    }

    private IEstadoPagamento mapStatusToEstado(StatusPagamento status) {
        if (status == null) {
            throw new IllegalArgumentException("O status não pode ser nulo");
        }
        return switch (status) {
            case PENDENTE_PROCESSAMENTO -> new EstadoPendente();
            case PROCESSADO_SUCESSO -> new EstadoProcessadoComSucesso();
            case PROCESSADO_FALHA -> new EstadoProcessadoComFalha();
            default -> throw new IllegalArgumentException("Status de pagamento desconhecido: " + status);
        };
    }
    
}
