package br.com.ntt.transacao.consumer.infra.consumer;

import br.com.ntt.transacao.consumer.application.usecases.ProcessarTransacao;
import br.com.ntt.transacao.consumer.domain.entities.transacao.Transacao;
import br.com.ntt.transacao.consumer.infra.consumer.dto.TransacaoDto;
import br.com.ntt.transacao.consumer.infra.consumer.mapper.TransacaoDtoMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericData;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TransacaoConsumer {

    private final ProcessarTransacao processarTransacao;

    private final TransacaoDtoMapper mapper;

    public TransacaoConsumer(ProcessarTransacao processarTransacao, TransacaoDtoMapper mapper) {
        this.processarTransacao = processarTransacao;
        this.mapper = mapper;
    }

    @KafkaListener(topics = "TRANSACAO-TOPIC", groupId = "group-1")
    public void process(TransacaoDto dto) throws JsonProcessingException {
        log.info("message processor step=start");

        Transacao transacao = mapper.toDomain(dto);

        processarTransacao.executar(transacao);

        log.info("message processor step=end");
    }

}
