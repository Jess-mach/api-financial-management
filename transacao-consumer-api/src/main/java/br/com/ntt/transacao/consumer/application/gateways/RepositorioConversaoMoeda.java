package br.com.ntt.transacao.consumer.application.gateways;

import br.com.ntt.transacao.consumer.domain.entities.SaldoConta;

public interface RepositorioConversaoMoeda {

    SaldoConta conversaoMoeda(String moeda);

}
