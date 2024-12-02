package com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.interfaces.repositories.useCases;

import java.util.List;

import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities.enuns.StatusPagamento;
public interface IPagamentoRespository<T, ID> {
    T create(T entity);
    T findById(ID id);
    List<T> findAll();
    T update(T entity,StatusPagamento statusPagamento);
    boolean delete(ID id);
    List<T> findByCodigoDebito(Integer codigoDebito);
    List<T> findByCpfOuCnpj(String cpfOuCnpj);
    List<T> findByStatus(String status);
}
