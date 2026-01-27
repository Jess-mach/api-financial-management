package br.com.ntt.transacao.producer.infra.gateways;

import br.com.ntt.transacao.producer.domain.entities.transacao.analise.AnaliseDeDespesaItem;
import br.com.ntt.transacao.producer.domain.model.StatusTransacao;
import br.com.ntt.transacao.producer.domain.entities.transacao.transacao.Transacao;
import br.com.ntt.transacao.producer.domain.model.TipoTransacao;
import br.com.ntt.transacao.producer.infra.persistence.AnaliseDeDespesaCampos;
import br.com.ntt.transacao.producer.infra.persistence.TransacaoEntity;

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

    public AnaliseDeDespesaItem toDomain(AnaliseDeDespesaCampos campos) {
        return new AnaliseDeDespesaItem(
                campos.getDataHoraSolicitacao(),
                TipoTransacao.getDoCodigo(campos.getTipo()),
                campos.getValor(),
                campos.getQuantidade()
        );
    }
}
