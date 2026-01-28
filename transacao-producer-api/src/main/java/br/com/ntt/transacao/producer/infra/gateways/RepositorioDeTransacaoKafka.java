package br.com.ntt.transacao.producer.infra.gateways;

import br.com.ntt.common.transacao.domain.entity.Transacao;
import br.com.ntt.transacao.producer.application.gateways.RepositorioProdutorDeTransacao;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
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
