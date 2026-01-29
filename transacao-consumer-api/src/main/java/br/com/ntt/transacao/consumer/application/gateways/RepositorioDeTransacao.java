package br.com.ntt.transacao.consumer.application.gateways;

import br.com.ntt.common.transacao.domain.entity.Transacao;

public interface RepositorioDeTransacao {

    Transacao atualizarTransacao(Transacao transacao);


}
