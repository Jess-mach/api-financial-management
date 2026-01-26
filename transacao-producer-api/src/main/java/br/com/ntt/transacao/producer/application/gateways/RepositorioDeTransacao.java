package br.com.ntt.transacao.producer.application.gateways;

import br.com.ntt.transacao.producer.domain.entities.transacao.Transacao;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.UUID;

public interface RepositorioDeTransacao {

    Transacao cadastrarTransacao(Transacao transacao);
    List<Transacao> listarTodos();
    Transacao busacarPorId (UUID id);

}
