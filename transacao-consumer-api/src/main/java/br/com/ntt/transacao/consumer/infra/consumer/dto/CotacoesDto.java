package br.com.ntt.transacao.consumer.infra.consumer.dto;

import br.com.ntt.transacao.consumer.domain.model.TipoBoletim;

import java.math.BigDecimal;

public record CotacoesDto(
        BigDecimal paridade_compra,
        BigDecimal paridade_venda,
        BigDecimal cotacao_compra,
        BigDecimal cotacao_venda,
        String data_hora_cotacao,
        String tipo_boletim) {
}
