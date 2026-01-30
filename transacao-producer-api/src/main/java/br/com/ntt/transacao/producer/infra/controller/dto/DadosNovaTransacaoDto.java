package br.com.ntt.transacao.producer.infra.controller.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.UUID;

public record DadosNovaTransacaoDto(
        @NotNull(message = "O ID do usuário é obrigatório")
        UUID usuarioId,

        @NotNull(message = "O valor é obrigatório")
        @DecimalMin(value = "0.01", message = "O valor deve ser maior que zero")
        BigDecimal valor,

        @NotNull(message = "O tipo da transação é obrigatório ")
        @Pattern(
                regexp = "DEPOSITO|SAQUE|COMPRA|TRANSFERENCIA|SAIDA_EM_DINHEIRO",
                message = "O tipo de transação deve ser DEPOSITO, SAQUE, COMPRA, TRANSFERENCIA ou SAIDA_EM_DINHEIRO"
        )
        String tipo,

        @NotBlank(message = "A descricao é obrigatória para análise de despesas")
        String descricao,

        @NotBlank(message = "A moeda é obrigatória")
        String moeda,

        @NotNull(message = "A conta é obrigatória")
        @DecimalMax(value = "50", message = "Id da conta deve ser até 50")
        Long conta
) {
}
