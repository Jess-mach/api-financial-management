package br.com.ntt.transacao.producer.application.usecases;

import br.com.ntt.common.transacao.domain.entity.Transacao;
import br.com.ntt.transacao.producer.application.gateways.RepositorioConsultaUsuario;
import br.com.ntt.transacao.producer.application.gateways.RepositorioDeTransacao;
import br.com.ntt.transacao.producer.application.gateways.RepositorioProdutorDeTransacao;
import org.springframework.stereotype.Component;

@Component
public class CriarTransacao {

    private final RepositorioDeTransacao repositorio;

    private final RepositorioProdutorDeTransacao repositorioProdutorDeTransacao;

    private final RepositorioConsultaUsuario repositorioConsultaUsuario;

    public CriarTransacao(RepositorioDeTransacao repositorio, RepositorioProdutorDeTransacao repositorioProdutorDeTransacao, RepositorioConsultaUsuario repositorioConsultaUsuario) {
        this.repositorio = repositorio;

        this.repositorioProdutorDeTransacao = repositorioProdutorDeTransacao;
        this.repositorioConsultaUsuario = repositorioConsultaUsuario;
    }

    public Transacao executar(Transacao transacao, String token) {
        repositorioConsultaUsuario.buscarPorId(transacao.getUsuarioId(), token);

        Transacao transacaoSalva = repositorio.cadastrarTransacao(transacao, token);

        repositorioProdutorDeTransacao.publicarTransacao(transacaoSalva);

        return transacaoSalva;
    }
}


