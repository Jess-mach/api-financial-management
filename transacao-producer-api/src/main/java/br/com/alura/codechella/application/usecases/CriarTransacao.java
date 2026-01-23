package br.com.alura.codechella.application.usecases;

import br.com.alura.codechella.application.gateways.RepositorioDeTransacao;
import br.com.alura.codechella.domain.entities.transacao.Transacao;

public class CriarTransacao {
    private final RepositorioDeTransacao repositorio;

    public CriarTransacao(RepositorioDeTransacao repositorio) {
        this.repositorio = repositorio;
    }

    public Transacao cadastrarUsuario(Transacao transacao) {
        return repositorio.cadastrarUsuario(transacao);
    }
}
