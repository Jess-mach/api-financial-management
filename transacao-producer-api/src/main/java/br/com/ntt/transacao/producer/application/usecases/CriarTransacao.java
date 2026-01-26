package br.com.ntt.transacao.producer.application.usecases;

import br.com.ntt.transacao.producer.application.gateways.RepositorioDeTransacao;
import br.com.ntt.transacao.producer.application.gateways.RepositorioProdutorDeTransacao;
import br.com.ntt.transacao.producer.domain.entities.transacao.Transacao;
import org.springframework.transaction.annotation.Transactional;

public class CriarTransacao {

    private final RepositorioDeTransacao repositorio;

    private final RepositorioProdutorDeTransacao repositorioProdutorDeTransacao;


    public CriarTransacao(RepositorioDeTransacao repositorio, RepositorioProdutorDeTransacao repositorioProdutorDeTransacao) {
        this.repositorio = repositorio;

        this.repositorioProdutorDeTransacao = repositorioProdutorDeTransacao;
    }

    public Transacao executar(Transacao transacao) {
        Transacao transacaoSalva = repositorio.cadastrarTransacao(transacao);

        repositorioProdutorDeTransacao.publicarTransacao(transacaoSalva);

        return transacaoSalva;
    }
}


