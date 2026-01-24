package br.com.ntt.transacao.consumer.application.gateways;

import br.com.ntt.transacao.consumer.domain.entities.transacao.Transacao;

import java.util.List;
import java.util.UUID;

public interface RepositorioDeTransacao {

    Transacao cadastrarTransacao(Transacao transacao);
    List<Transacao> listarTodos();
    Transacao busacarPorId (UUID id);

}
