package br.com.ntt.transacao.consumer.infra.consumer.dto;

public record SaldoContaDto(
        String name,
        String conta,
        Double saldo,
        String routingNumber,
        String id
) {
}
