package com.teste.processamento.pagamento.api.processamento_pagamento_api;

import com.teste.processamento.pagamento.api.processamento_pagamento_api.application.exeptions.EstadoInvalidoException;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.application.exeptions.PagamentoNaoEncontradoException;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.application.useCases.PagamentoUseCaseImpl;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities.enuns.StatusPagamento;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.infrastructure.model.PagamentoModel;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.interfaces.repositories.useCases.IPagamentoRespository;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.infrastructure.mapper.IPagamentoMapperModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PagamentoUseCaseImplTestFalha {

    @InjectMocks
    private PagamentoUseCaseImpl pagamentoUseCase;

    @Mock
    private IPagamentoRespository<PagamentoModel, Long> pagamentoRespository;

    @Mock
    private IPagamentoMapperModel pagamentoMapperModel;

    private PagamentoModel pagamentoModelMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        pagamentoModelMock = new PagamentoModel();
        pagamentoModelMock.setId(1L);
        pagamentoModelMock.setCodigoDebito(123456);
        pagamentoModelMock.setCpfOuCnpj("123.456.789-00");
        pagamentoModelMock.setStatus(StatusPagamento.PROCESSADO_SUCESSO);
    }

    @Test
    void naoDeveAtualizarPagamentoProcessadoComSucesso() {
        when(pagamentoRespository.findById(1L)).thenReturn(pagamentoModelMock);

        EstadoInvalidoException exception = assertThrows(EstadoInvalidoException.class, () -> {
            pagamentoUseCase.update(StatusPagamento.PENDENTE_PROCESSAMENTO, 1L);
        });

        assertEquals("Pagamentos processados com sucesso não podem ser alterados.", exception.getMessage());
        verify(pagamentoRespository, times(1)).findById(1L);
    }

    @Test
    void naoDeveExcluirPagamentoNaoPendente() {
        when(pagamentoRespository.findById(1L)).thenReturn(pagamentoModelMock);

        EstadoInvalidoException exception = assertThrows(EstadoInvalidoException.class, () -> {
            pagamentoUseCase.delete(1L);
        });

        assertEquals("Somente pagamentos pendentes podem ser excluídos logicamente.", exception.getMessage());
        verify(pagamentoRespository, times(1)).findById(1L);
    }

    @Test
    void naoDeveEncontrarPagamentoInexistentePorId() {
        when(pagamentoRespository.findById(1L)).thenThrow(new PagamentoNaoEncontradoException("Pagamento não encontrado."));

        PagamentoNaoEncontradoException exception = assertThrows(PagamentoNaoEncontradoException.class, () -> {
            pagamentoUseCase.findById(1L);
        });

        assertEquals("Pagamento não encontrado.", exception.getMessage());
        verify(pagamentoRespository, times(1)).findById(1L);
    }

    @Test
    void naoDeveAlterarParaStatusInvalido() {
        pagamentoModelMock.setStatus(StatusPagamento.PROCESSADO_FALHA);
        when(pagamentoRespository.findById(1L)).thenReturn(pagamentoModelMock);

        EstadoInvalidoException exception = assertThrows(EstadoInvalidoException.class, () -> {
            pagamentoUseCase.update(StatusPagamento.PROCESSADO_SUCESSO, 1L);
        });

        assertEquals("Pagamentos processados com  falha só podem ser alterados para Pendente de Processamento .", exception.getMessage());
        verify(pagamentoRespository, times(1)).findById(1L);
    }

    @Test
    void naoDeveRetornarListaVaziaDePagamentos() {
        when(pagamentoRespository.findAll()).thenReturn(Collections.emptyList());

        PagamentoNaoEncontradoException exception = assertThrows(PagamentoNaoEncontradoException.class, () -> {
            pagamentoUseCase.findAll();
        });

        assertEquals("Nenhum pagamento encontrado.", exception.getMessage());
        verify(pagamentoRespository, times(1)).findAll();
    }
}
