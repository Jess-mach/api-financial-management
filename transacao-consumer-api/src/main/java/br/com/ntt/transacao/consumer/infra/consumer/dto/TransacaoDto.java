package br.com.ntt.transacao.consumer.infra.consumer.dto;

import br.com.ntt.transacao.consumer.domain.model.StatusTransacao;
import br.com.ntt.transacao.consumer.domain.model.TipoTransacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransacaoDto(
        UUID id,
        UUID usuarioId,
        BigDecimal valor,
        TipoTransacao tipo,
        StatusTransacao status,
        LocalDateTime dataHoraSolicitacao,
        LocalDateTime dataHoraFinalizacao,
        String moeda,
        BigDecimal taxaCambio,
        String descricao) {
}
