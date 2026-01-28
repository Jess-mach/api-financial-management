package br.com.ntt.transacao.consumer.infra.consumer.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ConversorMoedaDto(
        String moeda,
        String data,
        List<CotacoesDto> cotacoes
) {
}
