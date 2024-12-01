package com.teste.processamento.pagamento.api.processamento_pagamento_api.application.useCases;

import com.teste.processamento.pagamento.api.processamento_pagamento_api.application.dtos.PagamentoRequestDTO;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.application.dtos.PagamentoResponseDTO;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities.Pagamento;

public interface IPagamentoMapperDTO {
    Pagamento toPagamento(PagamentoRequestDTO pagamentoRequestDTO);
    PagamentoRequestDTO toPagamentoRequestDto(Pagamento pagamento);
    PagamentoResponseDTO toPagamentoResponseDto(Pagamento pagamento);
}