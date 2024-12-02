package com.teste.processamento.pagamento.api.processamento_pagamento_api.application.useCases;

import java.util.List;

import org.springframework.stereotype.Service;

import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities.Pagamento;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities.enuns.StatusPagamento;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.interfaces.repositories.useCases.IPagamentoRespository;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.infrastructure.mapper.IPagamentoMapperModel;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.infrastructure.model.PagamentoModel;

@Service
public class PagamentoUseCaseImpl implements IPagamentoUseCase {

    private final IPagamentoRespository<PagamentoModel, Long> pagamentoRespository;

    private final IPagamentoMapperModel pagamentoMapperModel;

    public PagamentoUseCaseImpl(IPagamentoRespository<PagamentoModel, Long> pagamentoRespository,
                                IPagamentoMapperModel pagamentoMapperModel) {
        this.pagamentoRespository = pagamentoRespository;
        this.pagamentoMapperModel = pagamentoMapperModel;
    }

    @Override
    public PagamentoModel create(Pagamento pagamento) {
        PagamentoModel pagamentoModel = pagamentoMapperModel.toPagamentoModel(pagamento);
        return this.pagamentoRespository.create(pagamentoModel);
    }

    @Override
    public PagamentoModel findById(Long id) {
        PagamentoModel pagamentoModel = pagamentoRespository.findById(id);
        if (pagamentoModel == null) {
            throw new RuntimeException("Pagamento não encontrado");
        }
        return pagamentoModel;
    }

    @Override
    public List<PagamentoModel> findAll() {
        List<PagamentoModel> pagamentoModelList = pagamentoRespository.findAll();
        if (pagamentoModelList.isEmpty()) {
            throw new RuntimeException("Não existem pagamentos");
        }
        return pagamentoModelList;
    }

    @Override
    public PagamentoModel update(Pagamento pagamento, Long id) {
        PagamentoModel pagamentoModel = pagamentoMapperModel.toPagamentoModel(pagamento);
        PagamentoModel existingModel = findById(id);
        if (existingModel.getStatus().equals(StatusPagamento.PROCESSADO_SUCESSO)) {
            throw new RuntimeException("Pagamentos processados com sucesso não podem ser alterados");
        }
        return pagamentoRespository.update(pagamentoModel, id);
    }

    @Override
    public boolean delete(Long id) {
        PagamentoModel pagamentoModel = findById(id);
        if (pagamentoModel.getStatus().equals(StatusPagamento.PENDENTE_PROCESSAMENTO)) {
            pagamentoModel.setStatus(StatusPagamento.INATIVO);
            pagamentoRespository.update(pagamentoModel, id);
            return true;
        }
        throw new RuntimeException("Somente pagamentos pendentes podem ser excluídos logicamente.");
    }
}
