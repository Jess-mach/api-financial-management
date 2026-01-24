package br.com.ntt.transacao.producer.infra.controller.mapper;

import br.com.ntt.transacao.producer.domain.StatusTransacao;
import br.com.ntt.transacao.producer.domain.TipoTransacao;
import br.com.ntt.transacao.producer.domain.entities.transacao.Transacao;
import br.com.ntt.transacao.producer.infra.controller.dto.DadosNovaTransacao;
import br.com.ntt.transacao.producer.infra.controller.dto.TransacaoDto;
import jakarta.validation.Valid;

import java.time.LocalDateTime;
import java.util.UUID;

public class TransacaoDtoMapper {


    public Transacao toDomain(@Valid DadosNovaTransacao dados) {
        return new Transacao(
                UUID.randomUUID(),
                dados.usuarioId(),
                dados.valor(),
                TipoTransacao.valueOf(dados.tipo()),
                StatusTransacao.PENDENTE,
                LocalDateTime.now(),
                null,
                dados.moeda(),
                null,
                dados.descricao());
    }

    public TransacaoDto toDto(Transacao salvo) {
        return new TransacaoDto(
                salvo.getId(),
                salvo.getUsuarioId(),
                salvo.getValor(),
                salvo.getTipo(),
                salvo.getStatus(),
                salvo.getDataHoraSolicitacao(),
                salvo.getDataHoraFinalizacao(),
                salvo.getMoeda(),
                salvo.getTaxaCambio(),
                salvo.getDescricao());
    }
}
