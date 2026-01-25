package br.com.ntt.transacao.consumer.infra.gateways;

import br.com.ntt.transacao.consumer.application.gateways.RepositorioConversaoMoeda;
import br.com.ntt.transacao.consumer.domain.entities.SaldoConta;

public class RepositorioConversaoMoedaHttp implements RepositorioConversaoMoeda {

    @Override
    public SaldoConta conversaoMoeda(String moeda) {
        return null;
    }
}
