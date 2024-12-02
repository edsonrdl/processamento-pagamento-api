package com.teste.processamento.pagamento.api.processamento_pagamento_api.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities.enuns.StatusPagamento;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.infrastructure.model.PagamentoModel;

import java.util.List;

@Repository
public interface IPagamentoRepository extends JpaRepository<PagamentoModel, Long> {
    List<PagamentoModel> findByCodigoDebito(Integer codigoDebito);
    List<PagamentoModel> findByCpfOuCnpj(String cpfOuCnpj);
    List<PagamentoModel> findByStatus(StatusPagamento  status);
}
