package br.com.ntt.transacao.producer.application.usecases;

import br.com.ntt.transacao.producer.application.gateways.RepositorioDeTransacao;
import br.com.ntt.transacao.producer.domain.entities.transacao.Transacao;

public class CriarTransacao {

    private final RepositorioDeTransacao repositorio;

    public CriarTransacao(RepositorioDeTransacao repositorio) {
        this.repositorio = repositorio;
    }

    public Transacao cadastrarTransacao(Transacao transacao) {
        return repositorio.cadastrarTransacao(transacao);
    }
}

