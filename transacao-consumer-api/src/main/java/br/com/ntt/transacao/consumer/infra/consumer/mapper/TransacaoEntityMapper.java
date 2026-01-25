package br.com.ntt.transacao.consumer.infra.consumer.mapper;

import br.com.ntt.transacao.consumer.domain.StatusTransacao;
import br.com.ntt.transacao.consumer.domain.entities.transacao.Transacao;
import br.com.ntt.transacao.consumer.infra.persistence.TransacaoEntity;

import java.time.LocalDateTime;
import java.util.UUID;

public class TransacaoEntityMapper {

    public TransacaoEntity toEntity(Transacao dados){
        return new TransacaoEntity(
                UUID.randomUUID(),
                dados.getUsuarioId(),
                dados.getValor(),
                dados.getTipo(),
                StatusTransacao.PENDENTE,
                LocalDateTime.now(),
                null,
                dados.getMoeda(),
                null,
                dados.getDescricao());
    }

    public Transacao toDomain(TransacaoEntity entity){
        return new Transacao(
                entity.getId(),
                entity.getUsuarioId(),
                entity.getValor(),
                entity.getTipo(),
                entity.getStatus(),
                entity.getDataHoraSolicitacao(),
                entity.getDataHoraFinalizacao(),
                entity.getMoeda(),
                entity.getTaxaCambio(),
                entity.getDescricao());
    }
}
