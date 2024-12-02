package com.teste.processamento.pagamento.api.processamento_pagamento_api.application.useCases;

import org.springframework.stereotype.Component;

import com.teste.processamento.pagamento.api.processamento_pagamento_api.application.dtos.PagamentoRequestDTO;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.application.dtos.PagamentoResponseDTO;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities.Pagamento;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities.enuns.MetodoPagamento;

@Component
public class PagamentoMapperDTO implements IPagamentoMapperDTO {

    @Override
    public Pagamento toPagamento(PagamentoRequestDTO requestDTO) {
        if ((requestDTO.getMetodoPagamento() == MetodoPagamento.CARTAO_CREDITO ||
                requestDTO.getMetodoPagamento() == MetodoPagamento.CARTAO_DEBITO) &&
                (requestDTO.getNumeroCartao() == null || requestDTO.getNumeroCartao().isEmpty())) {
            throw new IllegalArgumentException(
                    "O número do cartão é obrigatório para pagamentos com cartão de crédito ou débito.");
        }

        return new Pagamento(
                requestDTO.getCodigoDebito(),
                requestDTO.getIdentificadorPagador(),
                requestDTO.getMetodoPagamento(),
                requestDTO.getNumeroCartao(),
                requestDTO.getValor());
    }

    @Override
    public PagamentoRequestDTO toPagamentoRequestDto(Pagamento pagamento) {
        PagamentoRequestDTO requestDTO = new PagamentoRequestDTO();
        requestDTO.setCodigoDebito(pagamento.getCodigoDebito());
        requestDTO.setIdentificadorPagador(pagamento.getIdentificadorPagador());
        requestDTO.setMetodoPagamento(pagamento.getMetodo());
        requestDTO.setNumeroCartao(pagamento.getNumeroCartao());
        requestDTO.setValor(pagamento.getValor());
        return requestDTO;
    }

    @Override
    public PagamentoResponseDTO toPagamentoResponseDto(Pagamento pagamento) {
        PagamentoResponseDTO responseDTO = new PagamentoResponseDTO();
        responseDTO.setId(pagamento.getId());
        responseDTO.setCodigoDebito(pagamento.getCodigoDebito());
        responseDTO.setIdentificadorPagador(pagamento.getIdentificadorPagador());
        responseDTO.setMetodoPagamento(pagamento.getMetodo());
        responseDTO.setNumeroCartao(pagamento.getNumeroCartao());
        responseDTO.setValor(pagamento.getValor());
        responseDTO.setStatus(pagamento.getStatus());
        return responseDTO;
    }
}
