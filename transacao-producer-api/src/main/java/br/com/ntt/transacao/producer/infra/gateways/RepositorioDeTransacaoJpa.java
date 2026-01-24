package br.com.ntt.transacao.producer.infra.gateways;

import br.com.ntt.transacao.producer.application.gateways.RepositorioDeTransacao;
import br.com.ntt.transacao.producer.domain.entities.transacao.Transacao;
import br.com.ntt.transacao.producer.infra.persistence.TransacaoEntity;
import br.com.ntt.transacao.producer.infra.persistence.TransacaoRepository;

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

    @Override
    public List<Transacao> listarTodos() {
        return repositorio.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Transacao busacarPorId(UUID id) {
        TransacaoEntity entity = repositorio.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Transação não encontrado"));
        return mapper.toDomain(entity);
    }
}
