package br.com.ntt.transacao.consumer.infra.gateways;

import br.com.ntt.transacao.consumer.application.gateways.RepositorioDeTransacao;
import br.com.ntt.transacao.consumer.domain.entities.transacao.Transacao;
import br.com.ntt.transacao.consumer.domain.model.StatusTransacao;
import br.com.ntt.transacao.consumer.infra.consumer.mapper.TransacaoEntityMapper;
import br.com.ntt.transacao.consumer.infra.persistence.TransacaoEntity;
import br.com.ntt.transacao.consumer.infra.persistence.TransacaoRepository;
import org.springframework.stereotype.Component;

@Component
public class RepositorioDeTransacaoJpa implements RepositorioDeTransacao {

    private final TransacaoRepository repositorio;
    private final TransacaoEntityMapper mapper;

    public RepositorioDeTransacaoJpa(TransacaoRepository repositorio, TransacaoEntityMapper mapper) {
        this.repositorio = repositorio;
        this.mapper = mapper;
    }

    @Override
    public Transacao atualizarTransacao(Transacao transacao) {

        TransacaoEntity entity = repositorio.findById(transacao.getId())
                .orElseThrow(() -> new IllegalArgumentException("Transação não encontrado"));

        if (entity.getStatus() != StatusTransacao.PENDENTE) return transacao;

        entity = mapper.toEntity(transacao, entity);

        repositorio.save(entity);

        return mapper.toDomain(entity);
    }
}
