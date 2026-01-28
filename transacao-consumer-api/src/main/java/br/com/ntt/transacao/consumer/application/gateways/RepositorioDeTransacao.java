package br.com.ntt.transacao.consumer.application.gateways;

import br.com.ntt.transacao.consumer.domain.entity.transacao.Transacao;

public interface RepositorioDeTransacao {

    Transacao atualizarTransacao(Transacao transacao);


}
