package br.com.alura.codechella.application.gateways;

import br.com.alura.codechella.domain.entities.transacao.Transacao;

import java.util.List;

public interface RepositorioDeTransacao {
    Transacao cadastrarUsuario(Transacao transacao);

    List<Transacao> listarTodos();
}
