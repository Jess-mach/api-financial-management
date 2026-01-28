package br.com.ntt.transacao.consumer.infra.consumer.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SaldoContaDto(
        String atualizadoEm,
        String nome,
        String conta,
        BigDecimal saldo,
        String id,
        BigDecimal limite
) {

}
