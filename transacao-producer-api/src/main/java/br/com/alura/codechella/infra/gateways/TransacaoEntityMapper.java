package br.com.alura.codechella.infra.gateways;

import br.com.alura.codechella.domain.entities.transacao.Transacao;
import br.com.alura.codechella.infra.persistence.TransacaoEntity;

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
