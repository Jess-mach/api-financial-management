package br.com.ntt.common.transacao.infra.controller.dto;

import br.com.ntt.common.transacao.domain.model.StatusTransacao;
import br.com.ntt.common.transacao.domain.model.TipoTransacao;

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
        String descricao,
        Long conta) {
}
