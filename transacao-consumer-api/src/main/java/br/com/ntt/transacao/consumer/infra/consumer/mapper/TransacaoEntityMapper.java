package br.com.ntt.transacao.consumer.infra.consumer.mapper;

import br.com.ntt.transacao.consumer.domain.entities.transacao.Transacao;
import br.com.ntt.transacao.consumer.infra.persistence.TransacaoEntity;

import java.time.LocalDateTime;

public class TransacaoEntityMapper {

    public TransacaoEntity toEntity(Transacao dadosAtualizados, TransacaoEntity entity){
        return new TransacaoEntity(
                entity.getId(),
                entity.getUsuarioId(),
                entity.getValor(),
                entity.getTipo(),
                dadosAtualizados.getStatus(),
                entity.getDataHoraSolicitacao(),
                LocalDateTime.now(),
                entity.getMoeda(),
                dadosAtualizados.getTaxaCambio(),
                entity.getDescricao());
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
