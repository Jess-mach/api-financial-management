package br.com.ntt.transacao.producer.application.usecases;

import br.com.ntt.transacao.producer.application.gateways.RepositorioDeTransacao;
import br.com.ntt.transacao.producer.domain.entities.transacao.Transacao;

import java.util.List;

public class ListarTransacao {

    private final RepositorioDeTransacao repositorio;

    public ListarTransacao(RepositorioDeTransacao repositorio) {
        this.repositorio = repositorio;
    }

    public List<Transacao> obterTodostransacao(){
        return this.repositorio.listarTodos();
    }
}

