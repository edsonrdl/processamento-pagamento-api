package com.teste.processamento.pagamento.api.processamento_pagamento_api.infrastructure.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities.enuns.MetodoPagamento;
import com.teste.processamento.pagamento.api.processamento_pagamento_api.domain.entities.enuns.StatusPagamento;

@Entity
@Table(name = "pagamentos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagamentoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo_debito", nullable = false)
    private Integer codigoDebito;

    @Column(name = "cpf_ou_cnpj", nullable = false, length = 14)
    private int cpfOuCnpj;

    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pagamento", nullable = false)
    private MetodoPagamento metodoPagamento;

    @Column(name = "numero_cartao")
    private String numeroCartao;

    @Column(name = "valor", nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_pagamento", nullable = false)
    private StatusPagamento status;
}
