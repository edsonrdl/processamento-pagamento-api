package com.teste.processamento.pagamento.api.processamento_pagamento_api.controller;

import com.teste.processamento.pagamento.api.processamento_pagamento_api.application.dtos.PagamentoRequestDTO;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.application.dtos.PagamentoResponseDTO;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.application.useCases.IPagamentoMapperDTO;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.application.useCases.IPagamentoUseCase;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities.Pagamento;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities.enuns.StatusPagamento;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.infrastructure.model.PagamentoModel;
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
    public ResponseEntity<PagamentoResponseDTO> createPagamento(@RequestBody PagamentoRequestDTO requestDTO) {
        Pagamento pagamento = pagamentoMapperDTO.toPagamento(requestDTO);
        PagamentoModel pagamentoModel = pagamentoUseCase.create(pagamento);
        Pagamento pagamentoMapped = pagamentoMapperModel.toPagamento(pagamentoModel);
        PagamentoResponseDTO responseDTO = pagamentoMapperDTO.toPagamentoResponseDto(pagamentoMapped);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
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

    @PutMapping("/{id}")
    public ResponseEntity<PagamentoResponseDTO> updatePagamento(@PathVariable Long id,
                                                                 @RequestBody PagamentoRequestDTO requestDTO) {
        try {
            Pagamento pagamento = pagamentoMapperDTO.toPagamento(requestDTO);
            PagamentoModel updatedModel = pagamentoUseCase.update(pagamento, id);
            Pagamento updatedPagamento = pagamentoMapperModel.toPagamento(updatedModel);
            PagamentoResponseDTO responseDTO = pagamentoMapperDTO.toPagamentoResponseDto(updatedPagamento);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePagamento(@PathVariable Long id) {
        try {
            pagamentoUseCase.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/{id}/processar")
    public ResponseEntity<PagamentoResponseDTO> processarPagamento(@PathVariable Long id) {
        try {
            PagamentoModel pagamentoModel = pagamentoUseCase.findById(id);
            Pagamento pagamento = pagamentoMapperModel.toPagamento(pagamentoModel);
            if (pagamento.getStatus().equals(StatusPagamento.PENDENTE_PROCESSAMENTO)) {
                pagamento.setStatus(StatusPagamento.PROCESSADO_SUCESSO);
                PagamentoModel updatedModel = pagamentoUseCase.update(pagamento, id);
                PagamentoResponseDTO responseDTO = pagamentoMapperDTO.toPagamentoResponseDto(
                        pagamentoMapperModel.toPagamento(updatedModel)
                );
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            }
            throw new RuntimeException("Pagamento não está pendente e não pode ser processado.");
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}/cancelar")
    public ResponseEntity<PagamentoResponseDTO> cancelarPagamento(@PathVariable Long id) {
        try {
            PagamentoModel pagamentoModel = pagamentoUseCase.findById(id);
            Pagamento pagamento = pagamentoMapperModel.toPagamento(pagamentoModel);
            if (pagamento.getStatus().equals(StatusPagamento.PENDENTE_PROCESSAMENTO)) {
                pagamento.setStatus(StatusPagamento.PROCESSADO_FALHA);
                PagamentoModel updatedModel = pagamentoUseCase.update(pagamento, id);
                PagamentoResponseDTO responseDTO = pagamentoMapperDTO.toPagamentoResponseDto(
                        pagamentoMapperModel.toPagamento(updatedModel)
                );
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            }
            throw new RuntimeException("Pagamento não está pendente e não pode ser cancelado.");
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/{id}/reprocessar")
    public ResponseEntity<PagamentoResponseDTO> reprocessarPagamento(@PathVariable Long id) {
        try {
            PagamentoModel pagamentoModel = pagamentoUseCase.findById(id);
            Pagamento pagamento = pagamentoMapperModel.toPagamento(pagamentoModel);
            if (pagamento.getStatus().equals(StatusPagamento.PROCESSADO_FALHA)) {
                pagamento.setStatus(StatusPagamento.PENDENTE_PROCESSAMENTO);
                PagamentoModel updatedModel = pagamentoUseCase.update(pagamento, id);
                PagamentoResponseDTO responseDTO = pagamentoMapperDTO.toPagamentoResponseDto(
                        pagamentoMapperModel.toPagamento(updatedModel)
                );
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            }
            throw new RuntimeException("Pagamento não pode ser reprocessado.");
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
