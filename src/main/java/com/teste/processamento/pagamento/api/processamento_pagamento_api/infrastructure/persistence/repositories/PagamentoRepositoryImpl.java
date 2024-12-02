package com.teste.processamento.pagamento.api.processamento_pagamento_api.infrastructure.persistence.repositories;

import com.teste.processamento.pagamento.api.processamento_pagamento_api.infrastructure.model.PagamentoModel;

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
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado para o ID: " + id));
    }

    @Override
    public List<PagamentoModel> findAll() {
        return pagamentoRepository.findAll(); 
    }

    @Override
    @Transactional
    public PagamentoModel update(PagamentoModel model, Long id) {
        PagamentoModel existingModel = this.findById(id); 

        existingModel.setCodigoDebito(model.getCodigoDebito());
        existingModel.setCpfOuCnpj(model.getCpfOuCnpj());
        existingModel.setMetodoPagamento(model.getMetodoPagamento());
        existingModel.setNumeroCartao(model.getNumeroCartao());
        existingModel.setValor(model.getValor());
        existingModel.setStatus(model.getStatus());

        return pagamentoRepository.save(existingModel); 
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

        throw new RuntimeException("Somente pagamentos pendentes podem ser excluídos logicamente.");
    }

    @Override
    public List<PagamentoModel> findByCodigoDebito(Integer codigoDebito) {
        return pagamentoRepository.findByCodigoDebito(codigoDebito); 
    }

    @Override
    public List<PagamentoModel> findByCpfOuCnpj(Integer cpfOuCnpj) {
        return pagamentoRepository.findByCpfOuCnpj(cpfOuCnpj); 
    }

    @Override
    public List<PagamentoModel> findByStatus(String status) {
        return pagamentoRepository.findByStatus(status); 
    }
}
