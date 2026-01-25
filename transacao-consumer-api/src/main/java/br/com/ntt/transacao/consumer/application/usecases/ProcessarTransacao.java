package br.com.ntt.transacao.consumer.application.usecases;

import br.com.ntt.transacao.consumer.application.gateways.RepositorioDeTransacao;
import br.com.ntt.transacao.consumer.application.gateways.RepositorioProdutorDeTransacao;
import br.com.ntt.transacao.consumer.domain.entities.transacao.Transacao;

public class ProcessarTransacao {

    private final RepositorioDeTransacao repositorio;

    private final RepositorioProdutorDeTransacao repositorioProdutorDeTransacao;


    public ProcessarTransacao(RepositorioDeTransacao repositorio, RepositorioProdutorDeTransacao repositorioProdutorDeTransacao) {
        this.repositorio = repositorio;

        this.repositorioProdutorDeTransacao = repositorioProdutorDeTransacao;
    }

    public Transacao executar(Transacao transacao) {
        Transacao transacaoSalva = repositorio.cadastrarTransacao(transacao);

        repositorioProdutorDeTransacao.publicarTransacao(transacaoSalva);

        return transacaoSalva;
    }
}


