package br.com.ntt.transacao.producer.application.gateways;

import br.com.ntt.transacao.producer.domain.entity.Transacao;

public interface RepositorioProdutorDeTransacao {

    void publicarTransacao(Transacao transacao);


}
