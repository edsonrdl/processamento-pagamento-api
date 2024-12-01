package com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.interfaces.repositories.useCases;

import java.util.List;

public interface IPagamentoRespository<T, ID> {
    T create(T model);
    T findById(ID id);
    List<T> findAll(T model);
    T  update(T model,ID id);
    boolean delete(ID id);
}
