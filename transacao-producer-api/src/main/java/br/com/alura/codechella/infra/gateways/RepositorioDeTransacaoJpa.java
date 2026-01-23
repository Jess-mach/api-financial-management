package br.com.alura.codechella.infra.gateways;

import br.com.alura.codechella.application.gateways.RepositorioDeTransacao;
import br.com.alura.codechella.domain.entities.transacao.Transacao;
import br.com.alura.codechella.infra.persistence.TransacaoEntity;
import br.com.alura.codechella.infra.persistence.TransacaoRepository;

import java.util.List;
import java.util.stream.Collectors;

public class RepositorioDeTransacaoJpa implements RepositorioDeTransacao {
    private final TransacaoRepository repositorio;
    private final TransacaoEntityMapper mapper;

    public RepositorioDeTransacaoJpa(TransacaoRepository repositorio, TransacaoEntityMapper mapper) {
        this.repositorio = repositorio;
        this.mapper = mapper;
    }

    @Override
    public Transacao cadastrarUsuario(Transacao transacao) {
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
}
