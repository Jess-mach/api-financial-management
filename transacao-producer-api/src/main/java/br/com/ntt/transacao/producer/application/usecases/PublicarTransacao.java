package br.com.ntt.transacao.producer.application.usecases;

import br.com.ntt.transacao.producer.application.gateways.RepositorioProdutorDeTransacao;
import br.com.ntt.transacao.producer.domain.entities.transacao.Transacao;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.UUID;

public class PublicarTransacao {

    private final RepositorioProdutorDeTransacao repositorioProdutorDeTransacao;

    private final KafkaTemplate<UUID, Transacao> kafkaTemplate;


    public PublicarTransacao(RepositorioProdutorDeTransacao repositorioProdutorDeTransacao, KafkaTemplate<UUID, Transacao> kafkaTemplate) {
        this.repositorioProdutorDeTransacao = repositorioProdutorDeTransacao;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publicarTransacao(Transacao transacao){
        kafkaTemplate.send("transacao-topic", transacao.getId(), transacao);

    }
}
