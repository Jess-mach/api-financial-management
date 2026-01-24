package br.com.ntt.transacao.producer.application.gateways;

import br.com.ntt.transacao.producer.domain.entities.transacao.Transacao;

public interface RepositorioProdutorDeTransacao {

    Transacao publicarTransacao(Transacao transacao);


}
