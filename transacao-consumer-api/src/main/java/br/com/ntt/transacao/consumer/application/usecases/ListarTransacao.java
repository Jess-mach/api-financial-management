package br.com.ntt.transacao.consumer.application.usecases;

import br.com.ntt.transacao.consumer.application.gateways.RepositorioDeTransacao;
import br.com.ntt.transacao.consumer.domain.entities.transacao.Transacao;

import java.util.List;

public class ListarTransacao {

    private final RepositorioDeTransacao repositorio;

    public ListarTransacao(RepositorioDeTransacao repositorio) {
        this.repositorio = repositorio;
    }

    public List<Transacao> listarTodos(){
        return this.repositorio.listarTodos();
    }

}

