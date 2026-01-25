package br.com.ntt.transacao.consumer.infra.gateways;

import br.com.ntt.transacao.consumer.application.gateways.RepositorioProdutorDeTransacao;
import br.com.ntt.transacao.consumer.domain.entities.transacao.Transacao;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.UUID;

public class RepositorioDeTransacaoKafka implements RepositorioProdutorDeTransacao{

    private final KafkaTemplate<UUID, Transacao> kafkaTemplate;


    public RepositorioDeTransacaoKafka(KafkaTemplate<UUID, Transacao> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publicarTransacao(Transacao transacao){
        kafkaTemplate.send("TRANSACAO-TOPIC-DLQ", transacao.getId(), transacao);

    }
}
