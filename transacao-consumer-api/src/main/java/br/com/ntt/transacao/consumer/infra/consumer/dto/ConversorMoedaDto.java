package br.com.ntt.transacao.consumer.infra.consumer.dto;

import java.util.List;

public record ConversorMoedaDto(
        String moeda,
        String data,
        List<CotacoesDto> cotacoes
) {
}
