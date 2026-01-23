package br.com.alura.codechella.application.gateways;

import br.com.alura.codechella.domain.entities.transacao.Transacao;

import java.util.List;
import java.util.UUID;

public interface RepositorioDeTransacao {

    Transacao cadastrarTransacao(Transacao transacao);
    List<Transacao> listarTodos();
    Transacao busacarPorId (UUID id);

}
