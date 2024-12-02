package com.teste.processamento.pagamento.api.processamento_pagamento_api;

import com.teste.processamento.pagamento.api.processamento_pagamento_api.application.exeptions.EstadoInvalidoException;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.application.exeptions.PagamentoNaoEncontradoException;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.application.useCases.PagamentoUseCaseImpl;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities.Pagamento;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities.enuns.MetodoPagamento;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities.enuns.StatusPagamento;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.infrastructure.mapper.IPagamentoMapperModel;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.infrastructure.model.PagamentoModel;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.interfaces.repositories.useCases.IPagamentoRespository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class PagamentoUseCaseImplTestSucesso {

    @InjectMocks
    private PagamentoUseCaseImpl pagamentoUseCase;

    @Mock
    private IPagamentoRespository<PagamentoModel, Long> pagamentoRespository;

    @Mock
    private IPagamentoMapperModel pagamentoMapperModel;

    private Pagamento pagamentoMock;
    private PagamentoModel pagamentoModelMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        pagamentoMock = new Pagamento(123456, "123.456.789-00", MetodoPagamento.BOLETO, null, new BigDecimal("250.50"));
        pagamentoModelMock = new PagamentoModel();
        pagamentoModelMock.setId(1L);
        pagamentoModelMock.setCodigoDebito(123456);
        pagamentoModelMock.setCpfOuCnpj("123.456.789-00");
        pagamentoModelMock.setMetodoPagamento(MetodoPagamento.BOLETO);
        pagamentoModelMock.setStatus(StatusPagamento.PENDENTE_PROCESSAMENTO);
        pagamentoModelMock.setValor(new BigDecimal("250.50"));
    }

    @Test
    void deveCriarPagamentoComSucesso() {

        when(pagamentoMapperModel.toPagamentoModel(pagamentoMock)).thenReturn(pagamentoModelMock);
        when(pagamentoRespository.create(any(PagamentoModel.class))).thenReturn(pagamentoModelMock);

        PagamentoModel result = pagamentoUseCase.create(pagamentoMock);

        assertNotNull(result);
        assertEquals(pagamentoModelMock.getCodigoDebito(), result.getCodigoDebito());
        verify(pagamentoMapperModel, times(1)).toPagamentoModel(pagamentoMock);
        verify(pagamentoRespository, times(1)).create(pagamentoModelMock);
    }

    @Test
    void deveLancarExcecaoQuandoAtualizarPagamentoComStatusProcessadoSucesso() {

        pagamentoModelMock.setStatus(StatusPagamento.PROCESSADO_SUCESSO);
        when(pagamentoRespository.findById(anyLong())).thenReturn(pagamentoModelMock);

        EstadoInvalidoException exception = assertThrows(
                EstadoInvalidoException.class,
                () -> pagamentoUseCase.update(StatusPagamento.PENDENTE_PROCESSAMENTO, 1L));

        assertEquals("Pagamentos processados com sucesso não podem ser alterados.", exception.getMessage());
        verify(pagamentoRespository, times(1)).findById(1L);
        verifyNoMoreInteractions(pagamentoRespository);
    }

    @Test
    void deveLancarExcecaoQuandoAtualizarPagamentoProcessadoComFalhaParaMesmoStatus() {

        pagamentoModelMock.setStatus(StatusPagamento.PROCESSADO_FALHA);
        when(pagamentoRespository.findById(anyLong())).thenReturn(pagamentoModelMock);

        EstadoInvalidoException exception = assertThrows(
                EstadoInvalidoException.class,
                () -> pagamentoUseCase.update(StatusPagamento.PROCESSADO_FALHA, 1L));

        assertEquals("Pagamentos processados com  falha só podem ser alterados para Pendente de Processamento .",
                exception.getMessage());
        verify(pagamentoRespository, times(1)).findById(1L);
        verifyNoMoreInteractions(pagamentoRespository);
    }

    @Test
    void deveLancarExcecaoQuandoExcluirPagamentoNaoPendente() {

        pagamentoModelMock.setStatus(StatusPagamento.PROCESSADO_SUCESSO);
        when(pagamentoRespository.findById(anyLong())).thenReturn(pagamentoModelMock);

        EstadoInvalidoException exception = assertThrows(
                EstadoInvalidoException.class,
                () -> pagamentoUseCase.delete(1L));

        assertEquals("Somente pagamentos pendentes podem ser excluídos logicamente.", exception.getMessage());
        verify(pagamentoRespository, times(1)).findById(1L);
        verifyNoMoreInteractions(pagamentoRespository);
    }

    @Test
    void deveLancarExcecaoQuandoPagamentoNaoEncontrado() {

        when(pagamentoRespository.findById(anyLong()))
                .thenThrow(new PagamentoNaoEncontradoException("Pagamento não encontrado."));

        PagamentoNaoEncontradoException exception = assertThrows(
                PagamentoNaoEncontradoException.class,
                () -> pagamentoUseCase.findById(1L));

        assertEquals("Pagamento não encontrado.", exception.getMessage());
        verify(pagamentoRespository, times(1)).findById(1L);
    }

    @Test
    void deveLancarExcecaoQuandoNenhumPagamentoEncontrado() {

        when(pagamentoRespository.findAll()).thenReturn(List.of());

        PagamentoNaoEncontradoException exception = assertThrows(
                PagamentoNaoEncontradoException.class,
                () -> pagamentoUseCase.findAll());

        assertEquals("Nenhum pagamento encontrado.", exception.getMessage());
        verify(pagamentoRespository, times(1)).findAll();
    }

    @Test
    void deveLancarExcecaoQuandoNenhumPagamentoEncontradoPorCodigoDebito() {

        when(pagamentoRespository.findByCodigoDebito(anyInt())).thenReturn(List.of());

        PagamentoNaoEncontradoException exception = assertThrows(
                PagamentoNaoEncontradoException.class,
                () -> pagamentoUseCase.findByCodigoDebito(123456));

        assertEquals("Nenhum pagamento encontrado.", exception.getMessage());
        verify(pagamentoRespository, times(1)).findByCodigoDebito(123456);
    }

    @Test
    void deveEncontrarPagamentoPorId() {

        when(pagamentoRespository.findById(1L)).thenReturn(pagamentoModelMock);

        PagamentoModel result = pagamentoUseCase.findById(1L);

        assertNotNull(result);
        assertEquals(pagamentoModelMock.getId(), result.getId());
        verify(pagamentoRespository, times(1)).findById(1L);
    }

}