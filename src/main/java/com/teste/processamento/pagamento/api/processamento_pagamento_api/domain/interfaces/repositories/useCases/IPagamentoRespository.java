package com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.interfaces.repositories.useCases;

import java.util.List;
public interface IPagamentoRespository<T, ID> {
    T create(T entity);
    T findById(ID id);
    List<T> findAll();
    T update(T entity, ID id);
    boolean delete(ID id);
    List<T> findByCodigoDebito(Integer codigoDebito);
    List<T> findByCpfOuCnpj(String cpfOuCnpj);
    List<T> findByStatus(String status);
}
