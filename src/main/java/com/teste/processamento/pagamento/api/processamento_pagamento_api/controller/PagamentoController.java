package com.teste.processamento.pagamento.api.processamento_pagamento_api.controller;

import com.teste.processamento.pagamento.api.processamento_pagamento_api.application.dtos.PagamentoRequestDTO;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.application.dtos.PagamentoResponseDTO;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.application.dtos.PagamentoUpdateRequestDTO;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.application.useCases.IPagamentoMapperDTO;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.application.useCases.IPagamentoUseCase;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities.Pagamento;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities.enuns.StatusPagamento;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.infrastructure.model.PagamentoModel;

import io.swagger.v3.oas.annotations.Operation;

import com.teste.processamento.pagamento.api.processamento_pagamento_api.infrastructure.mapper.IPagamentoMapperModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    private final IPagamentoMapperDTO pagamentoMapperDTO;
    private final IPagamentoMapperModel pagamentoMapperModel;
    private final IPagamentoUseCase pagamentoUseCase;

    @Autowired
    public PagamentoController(IPagamentoMapperDTO pagamentoMapperDTO,
            IPagamentoMapperModel pagamentoMapperModel,
            IPagamentoUseCase pagamentoUseCase) {
        this.pagamentoMapperDTO = pagamentoMapperDTO;
        this.pagamentoMapperModel = pagamentoMapperModel;
        this.pagamentoUseCase = pagamentoUseCase;
    }

    @PostMapping
    @Operation(summary = "Registrar um novo pagamento", description = """
                Registra um novo pagamento no sistema com o status inicial 'PENDENTE_PROCESSAMENTO'.
                - Método de pagamento pode ser 'PIX', 'BOLETO', 'CARTAO_CREDITO' ou 'CARTAO_DEBITO'.
                - Caso seja 'CARTAO_CREDITO' ou 'CARTAO_DEBITO', o número do cartão deve ser informado.
            """)
    public ResponseEntity<PagamentoResponseDTO> createPagamento(@RequestBody PagamentoRequestDTO requestDTO) {
        Pagamento pagamento = pagamentoMapperDTO.toPagamento(requestDTO);
        PagamentoModel pagamentoModel = pagamentoUseCase.create(pagamento);
        Pagamento pagamentoMapped = pagamentoMapperModel.toPagamento(pagamentoModel);
        PagamentoResponseDTO responseDTO = pagamentoMapperDTO.toPagamentoResponseDto(pagamentoMapped);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter informações de um pagamento", description = "Retorna os dados de um pagamento pelo ID fornecido.")
    public ResponseEntity<PagamentoResponseDTO> getPagamentoById(@PathVariable Long id) {
        try {
            PagamentoModel pagamentoModel = pagamentoUseCase.findById(id);
            Pagamento pagamento = pagamentoMapperModel.toPagamento(pagamentoModel);
            PagamentoResponseDTO responseDTO = pagamentoMapperDTO.toPagamentoResponseDto(pagamento);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    @Operation(summary = "Listar todos os pagamentos", description = "Retorna todos os pagamentos registrados no sistema.")
    public ResponseEntity<List<PagamentoResponseDTO>> getAllPagamentos() {
        try {
            List<PagamentoModel> pagamentos = pagamentoUseCase.findAll();
            List<PagamentoResponseDTO> responseDTOList = pagamentos.stream()
                    .map(pagamentoMapperModel::toPagamento)
                    .map(pagamentoMapperDTO::toPagamentoResponseDto)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(responseDTOList, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/filtro/codigoDebito/{codigoDebito}")
    @Operation(summary = "Filtrar pagamentos por código de débito", description = "Filtra os pagamentos com base no código de débito informado.")
    public ResponseEntity<List<PagamentoResponseDTO>> getByCodigoDebito(@PathVariable Integer codigoDebito) {
        List<PagamentoModel> pagamentos = pagamentoUseCase.findByCodigoDebito(codigoDebito);
        List<PagamentoResponseDTO> responseDTOList = pagamentos.stream()
                .map(pagamentoMapperModel::toPagamento)
                .map(pagamentoMapperDTO::toPagamentoResponseDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(responseDTOList, HttpStatus.OK);
    }

    @GetMapping("/filtro/cpfOuCnpj/{cpfOuCnpj}")
    @Operation(summary = "Filtrar pagamentos por CPF ou CNPJ", description = "Filtra os pagamentos com base no CPF ou CNPJ informado.")
    public ResponseEntity<List<PagamentoResponseDTO>> getByCpfOuCnpj(@PathVariable String cpfOuCnpj) {
        List<PagamentoModel> pagamentos = pagamentoUseCase.findByCpfOuCnpj(cpfOuCnpj);
        List<PagamentoResponseDTO> responseDTOList = pagamentos.stream()
                .map(pagamentoMapperModel::toPagamento)
                .map(pagamentoMapperDTO::toPagamentoResponseDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(responseDTOList, HttpStatus.OK);
    }

    @GetMapping("/filtro/status/{status}")
    @Operation(summary = "Filtrar pagamentos por status", description = "Filtra os pagamentos com base no status informado.")
    public ResponseEntity<List<PagamentoResponseDTO>> getByStatus(@PathVariable StatusPagamento status) {
        List<PagamentoModel> pagamentos = pagamentoUseCase.findByStatus(status);
        List<PagamentoResponseDTO> responseDTOList = pagamentos.stream()
                .map(pagamentoMapperModel::toPagamento)
                .map(pagamentoMapperDTO::toPagamentoResponseDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(responseDTOList, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um pagamento", description = """
                Atualiza os dados de um pagamento. Pagamentos com o status 'PROCESSADO_SUCESSO' não podem ser alterados.
            """)
    public ResponseEntity<PagamentoResponseDTO> updatePagamento(@RequestBody PagamentoUpdateRequestDTO requestDTO) {
        try {
            PagamentoModel model = pagamentoUseCase.findById(requestDTO.getCodigoDebito());
            Pagamento pagamento = pagamentoMapperModel.toPagamento(model);

            pagamento.alterarStatus(requestDTO.getStatus());

            PagamentoModel updatedModel = pagamentoMapperModel.toPagamentoModel(pagamento);
            pagamentoUseCase.update(requestDTO.getStatus(), requestDTO.getCodigoDebito());
            Pagamento pagamentoValidation = pagamentoMapperModel.toPagamento(updatedModel);

            PagamentoResponseDTO responseDTO = pagamentoMapperDTO.toPagamentoResponseDto(pagamentoValidation);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir pagamento logicamente", description = """
                Exclui logicamente um pagamento alterando seu status para 'INATIVO'. Somente pagamentos pendentes podem ser excluídos.
            """)
    public ResponseEntity<Void> deletePagamento(@PathVariable Long id) {
        try {
            pagamentoUseCase.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
