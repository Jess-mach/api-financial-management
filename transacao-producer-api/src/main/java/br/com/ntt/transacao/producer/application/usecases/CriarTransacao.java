package br.com.ntt.transacao.producer.application.usecases;

import br.com.ntt.common.transacao.domain.entity.Transacao;
import br.com.ntt.transacao.producer.application.gateways.RepositorioDeTransacao;
import br.com.ntt.transacao.producer.application.gateways.RepositorioProdutorDeTransacao;
import org.springframework.stereotype.Component;

@Component
public class CriarTransacao {

    private final RepositorioDeTransacao repositorio;

    private final RepositorioProdutorDeTransacao repositorioProdutorDeTransacao;


    public CriarTransacao(RepositorioDeTransacao repositorio, RepositorioProdutorDeTransacao repositorioProdutorDeTransacao) {
        this.repositorio = repositorio;

        this.repositorioProdutorDeTransacao = repositorioProdutorDeTransacao;
    }

    public Transacao executar(Transacao transacao, String token) {
        Transacao transacaoSalva = repositorio.cadastrarTransacao(transacao, token);

        repositorioProdutorDeTransacao.publicarTransacao(transacaoSalva);

        return transacaoSalva;
    }
}


