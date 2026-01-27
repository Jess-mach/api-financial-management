package br.com.ntt.transacao.producer.infra.gateways;

import br.com.ntt.transacao.producer.application.gateways.RepositorioProdutorDeTransacao;
import br.com.ntt.transacao.producer.domain.entities.transacao.transacao.Transacao;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.UUID;

public class RepositorioDeTransacaoKafka implements RepositorioProdutorDeTransacao{

    private final KafkaTemplate<UUID, Transacao> kafkaTemplate;


    public RepositorioDeTransacaoKafka(KafkaTemplate<UUID, Transacao> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publicarTransacao(Transacao transacao){
        kafkaTemplate.send("TRANSACAO-TOPIC", transacao.getId(), transacao);

    }
}
