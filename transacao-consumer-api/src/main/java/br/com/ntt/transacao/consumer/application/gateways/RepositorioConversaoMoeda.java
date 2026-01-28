package br.com.ntt.transacao.consumer.application.gateways;

import br.com.ntt.transacao.consumer.domain.entity.moeda.ConversorMoeda;

import java.time.LocalDateTime;

public interface RepositorioConversaoMoeda {

    ConversorMoeda conversaoMoeda(String moeda, LocalDateTime dataHoraSolicitacao);

}
