package com.teste.processamento.pagamento.api.processamento_pagamento_api.infrastructure.persistence.repositories;


import com.teste.processamento.pagamento.api.processamento_pagamento_api.infrastructure.model.PagamentoModel;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.application.exeptions.PagamentoNaoEncontradoException;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities.enuns.StatusPagamento;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.interfaces.repositories.useCases.IPagamentoRespository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PagamentoRepositoryImpl implements IPagamentoRespository<PagamentoModel, Long> {

    private final IPagamentoRepository pagamentoRepository;

    public PagamentoRepositoryImpl(IPagamentoRepository pagamentoRepository) {
        this.pagamentoRepository = pagamentoRepository;
    }

    @Override
    public PagamentoModel create(PagamentoModel model) {
        return pagamentoRepository.save(model);
    }

    @Override
    public PagamentoModel findById(Long id) {
        return pagamentoRepository.findById(id)
                .orElseThrow(() -> new PagamentoNaoEncontradoException("Pagamento não encontrado para o ID: " + id));
    }

    @Override
    public List<PagamentoModel> findAll() {
        return pagamentoRepository.findAll();
    }

    @Override
    @Transactional
    public PagamentoModel update(PagamentoModel model, StatusPagamento statusPagamento) {
        model.setStatus(statusPagamento);
        return pagamentoRepository.save(model);
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        PagamentoModel model = this.findById(id);

        if (model.getStatus().equals(StatusPagamento.PENDENTE_PROCESSAMENTO)) {
            model.setStatus(StatusPagamento.INATIVO);
            pagamentoRepository.save(model);
            return true;
        }

        throw new PagamentoNaoEncontradoException("Somente pagamentos pendentes podem ser excluídos logicamente.");
    }

    @Override
    public List<PagamentoModel> findByCodigoDebito(Integer codigoDebito) {
        return pagamentoRepository.findByCodigoDebito(codigoDebito);
    }

    @Override
    public List<PagamentoModel> findByCpfOuCnpj(String cpfOuCnpj) {
        return pagamentoRepository.findByCpfOuCnpj(cpfOuCnpj);
    }

    @Override
    public List<PagamentoModel> findByStatus(String status) {
        return pagamentoRepository.findByStatus(status);
    }
}
