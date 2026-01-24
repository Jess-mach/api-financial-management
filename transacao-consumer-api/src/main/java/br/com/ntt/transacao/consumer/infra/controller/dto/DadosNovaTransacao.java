package br.com.ntt.transacao.consumer.infra.controller.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record DadosNovaTransacao(
        @NotNull
        UUID usuarioId,

        @NotNull(message = "O valor é obrigatório")
        @DecimalMin(value = "0.01", message = "O valor deve ser maior que zero")
        BigDecimal valor,

        @NotNull(message = "O tipo da transação é obrigatório ")
        String tipo,

        @NotBlank(message = "A descricao é obrigatória para análise de despesas")
        String descricao,

        @NotBlank
        String moeda
) {
}
