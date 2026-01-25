package br.com.ntt.transacao.consumer.infra.gateways;

import br.com.ntt.transacao.consumer.application.gateways.RepositorioDeTransacao;
import br.com.ntt.transacao.consumer.domain.entities.transacao.Transacao;
import br.com.ntt.transacao.consumer.infra.persistence.TransacaoEntity;
import br.com.ntt.transacao.consumer.infra.persistence.TransacaoRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class RepositorioDeTransacaoJpa implements RepositorioDeTransacao {

    private final TransacaoRepository repositorio;
    private final TransacaoEntityMapper mapper;

    public RepositorioDeTransacaoJpa(TransacaoRepository repositorio, TransacaoEntityMapper mapper) {
        this.repositorio = repositorio;
        this.mapper = mapper;
    }

    @Override
    public Transacao cadastrarTransacao(Transacao transacao) {
        TransacaoEntity entity = mapper.toEntity(transacao);
        repositorio.save(entity);
        return mapper.toDomain(entity);
    }


    //    @Override
    public Transacao busacarPorId(UUID id) {
        TransacaoEntity entity = repositorio.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Transação não encontrado"));
        return mapper.toDomain(entity);
    }
}
