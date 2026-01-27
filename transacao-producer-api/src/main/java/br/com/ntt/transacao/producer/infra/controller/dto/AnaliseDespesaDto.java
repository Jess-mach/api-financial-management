package br.com.ntt.transacao.producer.infra.controller.dto;


public record AnaliseDespesaDto(
        TotalizadorDespesaDto dia,
        TotalizadorDespesaDto mes
) {
}
