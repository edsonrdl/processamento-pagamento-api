package com.teste.processamento.pagamento.api.processamento_pagamento_api.application.useCases;

import com.teste.processamento.pagamento.api.processamento_pagamento_api.application.exeptions.EstadoInvalidoException;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.application.exeptions.PagamentoNaoEncontradoException;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities.Pagamento;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities.enuns.StatusPagamento;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.interfaces.repositories.useCases.IPagamentoRespository;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.infrastructure.mapper.IPagamentoMapperModel;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.infrastructure.model.PagamentoModel;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return pagamentoRespository.create(pagamentoModel);
    }

    @Override
    public PagamentoModel findById(Long id) {
        return pagamentoRespository.findById(id);
    }

    @Override
    public List<PagamentoModel> findAll() {
        List<PagamentoModel> pagamentoModelList = pagamentoRespository.findAll();
        if (pagamentoModelList.isEmpty()) {
            throw new PagamentoNaoEncontradoException("Nenhum pagamento encontrado.");
        }
        return pagamentoModelList;
    }

    @Override
    public PagamentoModel update(StatusPagamento statusPagamento, Long id) {
        PagamentoModel existingModel = pagamentoRespository.findById(id);

        if (existingModel.getStatus().equals(StatusPagamento.PROCESSADO_SUCESSO)) {
            throw new EstadoInvalidoException("Pagamentos processados com sucesso não podem ser alterados.");
        }
        if (existingModel.getStatus().equals(StatusPagamento.PROCESSADO_FALHA) && statusPagamento.equals(StatusPagamento.PROCESSADO_FALHA)) {
            throw new EstadoInvalidoException("Pagamentos processados com  falha só podem ser alterados para Pendente de Processamento .");
        }
        return pagamentoRespository.update(existingModel, statusPagamento);
    }

    @Override
    public boolean delete(Long id) {
        PagamentoModel pagamentoModel = pagamentoRespository.findById(id);

        if (!pagamentoModel.getStatus().equals(StatusPagamento.PENDENTE_PROCESSAMENTO)) {
            throw new EstadoInvalidoException("Somente pagamentos pendentes podem ser excluídos logicamente.");
        }
        StatusPagamento  statusPagamento=StatusPagamento.INATIVO;
        pagamentoRespository.update(pagamentoModel, statusPagamento);
        return true;
    }

    @Override
    public List<PagamentoModel> findByCodigoDebito(Integer codigoDebito) {
        return pagamentoRespository.findByCodigoDebito(codigoDebito);
    }

    @Override
    public List<PagamentoModel> findByCpfOuCnpj(String cpfOuCnpj) {
        return pagamentoRespository.findByCpfOuCnpj(cpfOuCnpj);
    }

    @Override
    public List<PagamentoModel> findByStatus(String status) {
        return pagamentoRespository.findByStatus(status);
    }
}
