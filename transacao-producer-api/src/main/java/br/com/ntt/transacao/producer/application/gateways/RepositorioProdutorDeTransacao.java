package br.com.ntt.transacao.producer.application.gateways;


import br.com.ntt.common.transacao.domain.entity.Transacao;

public interface RepositorioProdutorDeTransacao {

    void publicarTransacao(Transacao transacao);


}
