package com.teste.processamento.pagamento.api.processamento_pagamento_api.infrastructure.persistence.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities.Pagamento;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.interfaces.repositories.useCases.IPagamentoRespository;


public interface PagamentoRepository extends JpaRepository<Pagamento, Long>, IPagamentoRespository {
    
}