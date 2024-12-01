package com.teste.processamento.pagamento.api.processamento_pagamento_api.application.useCases;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities.Pagamento;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.infrastructure.mapper.IPagamentoMapperModel;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.infrastructure.model.PagamentoModel;

@Service
public class PagamentoUseCaseImpl implements IPagamentoUseCase {

    private final IPagamentoDAO pagamentoDAO;

    private final IPagamentoMapperModel pagamentoMapperModel;

    public PagamentoUseCaseImpl(IPagamentoDAO pagamentoDAO, IPagamentoMapperModel pagamentoMapperModel) {
        this.pagamentoDAO = pagamentoDAO;
        this.pagamentoMapperModel = pagamentoMapperModel;
    }

    @Override
    public PagamentoModel create(Pagamento pagamento) {
        PagamentoModel pagamentoModel = this.pagamentoMapperModel.toPagamentoModel(pagamento);
        return this.pagamentoDAO.create(pagamentoModel);
    }

    @Override
    public PagamentoModel findById(Long id) {
        PagamentoModel pagamentoModel = this.pagamentoDAO.findById(id);
        if (pagamentoModel == null) {
            throw new RuntimeException("Pagamento não encontrado");
        }
        return pagamentoModel;
    }

    @Override
    @Cacheable(value = "pagamentos", key = "#page + '-' + #size")
    public Map<String, Object> findAll(int page, int size) {
        List<PagamentoModel> pagamentoModelList = this.pagamentoDAO.findAll(page, size);
        if (pagamentoModelList == null || pagamentoModelList.isEmpty()) {
            throw new RuntimeException("Não existem pagamentos");
        }
        long totalPagamentos = this.pagamentoDAO.countTotal();

        Map<String, Object> result = new HashMap<>();
        result.put("pagamentos", pagamentoModelList);
        result.put("total", totalPagamentos);

        return result;
    }

    @Override
    public PagamentoModel update(Pagamento pagamento, Long id) {
        PagamentoModel pagamentoModel = this.pagamentoMapperModel.toPagamentoModel(pagamento);
        return this.pagamentoDAO.update(pagamentoModel, id);
    }

    @Override
    public boolean delete(Long id) {
        PagamentoModel pagamentoModel = this.pagamentoDAO.findById(id);
        if (pagamentoModel == null) {
            throw new RuntimeException("Pagamento não encontrado");
        }
        this.pagamentoDAO.delete(id);
        return true;
    }
}