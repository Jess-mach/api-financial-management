package br.com.alura.codechella.application.usecases;

import br.com.alura.codechella.application.gateways.RepositorioDeTransacao;
import br.com.alura.codechella.domain.entities.transacao.Transacao;

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

