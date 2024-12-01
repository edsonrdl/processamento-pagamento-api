package com.teste.processamento.pagamento.api.processamento_pagamento_api.application.useCases;

import org.springframework.stereotype.Component;

@Component
public class PagamentoMapperDTO implements IPagamentoMapperDTO {

    @Override
    public Pagamento toPagamento(PagamentoRequestDTO requestDTO) {
        Pagamento pagamento = new Pagamento();
        pagamento.setCodigoDebito(requestDTO.getCodigoDebito());
        pagamento.setCpfOuCnpj(requestDTO.getCpfOuCnpj());
        pagamento.setMetodoPagamento(requestDTO.getMetodoPagamento());
        pagamento.setNumeroCartao(requestDTO.getNumeroCartao());
        pagamento.setValor(requestDTO.getValor());
        return pagamento;
    }

    @Override
    public PagamentoRequestDTO toPagamentoRequestDto(Pagamento pagamento) {
        PagamentoRequestDTO requestDTO = new PagamentoRequestDTO();
        requestDTO.setCodigoDebito(pagamento.getCodigoDebito());
        requestDTO.setCpfOuCnpj(pagamento.getCpfOuCnpj());
        requestDTO.setMetodoPagamento(pagamento.getMetodoPagamento());
        requestDTO.setNumeroCartao(pagamento.getNumeroCartao());
        requestDTO.setValor(pagamento.getValor());
        return requestDTO;
    }

    @Override
    public PagamentoResponseDTO toPagamentoResponseDto(Pagamento pagamento) {
        PagamentoResponseDTO responseDTO = new PagamentoResponseDTO();
        responseDTO.setId(pagamento.getId());
        responseDTO.setCodigoDebito(pagamento.getCodigoDebito());
        responseDTO.setCpfOuCnpj(pagamento.getCpfOuCnpj());
        responseDTO.setMetodoPagamento(pagamento.getMetodoPagamento());
        responseDTO.setValor(pagamento.getValor());
        responseDTO.setEstado(pagamento.getEstado().getClass().getSimpleName());
        return responseDTO;
    }
}