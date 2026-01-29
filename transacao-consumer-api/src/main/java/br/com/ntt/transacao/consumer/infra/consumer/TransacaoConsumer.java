package br.com.ntt.transacao.consumer.infra.consumer;

import br.com.ntt.common.transacao.domain.entity.Transacao;
import br.com.ntt.common.transacao.infra.controller.dto.TransacaoDto;
import br.com.ntt.common.transacao.infra.controller.mapper.TransacaoDtoMapper;
import br.com.ntt.transacao.consumer.application.usecases.ProcessarTransacao;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
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

    @KafkaHandler
    @KafkaListener(topics = "TRANSACAO-TOPIC-DLQ", groupId = "group-1")
    public void processarDLQ(TransacaoDto mensagem) {
        log.info("message processor DLQ step=start");

        Transacao transacao = mapper.toDomain(mensagem);

        processarTransacao.atualizarStatusErro(transacao);

        log.info("message processor DLQ step=end");
    }

}
