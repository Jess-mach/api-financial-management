package br.com.ntt.transacao.consumer.infra.consumer.dto;

import java.math.BigDecimal;

public record SaldoContaDto(
        String createdAt,
        String name,
        String conta,
        BigDecimal saldo,
        String routingNumber,
        String id
) {
}
