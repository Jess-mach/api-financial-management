package br.com.ntt.transacao.producer.infra.gateways;

import br.com.ntt.transacao.producer.domain.entities.transacao.Transacao;
import br.com.ntt.transacao.producer.infra.persistence.TransacaoEntity;

public class TransacaoEntityMapper {

    public TransacaoEntity toEntity(Transacao transacao){
        return new TransacaoEntity(transacao.getUsuarioId(), transacao.getNome(),
                transacao.getNascimento(), transacao.getEmail());
    }

    public Transacao toDomain(TransacaoEntity entity){
        return new Transacao(entity.getUsuarioId(), entity.getNome(), entity.getNascimento(),
                entity.getEmail());
    }
}
