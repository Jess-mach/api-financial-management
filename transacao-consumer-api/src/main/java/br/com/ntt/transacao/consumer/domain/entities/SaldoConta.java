package br.com.ntt.transacao.consumer.domain.entities;

public record SaldoConta(
        String name,
        String conta,
        Double saldo,
        String routingNumber,
        String id
) {
}
