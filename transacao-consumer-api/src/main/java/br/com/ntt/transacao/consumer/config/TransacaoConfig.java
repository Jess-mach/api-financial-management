package br.com.ntt.transacao.consumer.config;

import br.com.ntt.transacao.consumer.application.gateways.RepositorioDeTransacao;
import br.com.ntt.transacao.consumer.application.gateways.RepositorioProdutorDeTransacao;
import br.com.ntt.transacao.consumer.application.usecases.BuscarTransacaoPorId;
import br.com.ntt.transacao.consumer.application.usecases.CriarTransacao;
import br.com.ntt.transacao.consumer.application.usecases.ListarTransacao;
import br.com.ntt.transacao.consumer.infra.gateways.RepositorioDeTransacaoKafka;
import br.com.ntt.transacao.consumer.domain.entities.transacao.Transacao;
import br.com.ntt.transacao.consumer.infra.controller.mapper.TransacaoDtoMapper;
import br.com.ntt.transacao.consumer.infra.gateways.RepositorioDeTransacaoJpa;
import br.com.ntt.transacao.consumer.infra.gateways.TransacaoEntityMapper;
import br.com.ntt.transacao.consumer.infra.persistence.TransacaoRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.UUID;

@Configuration
public class TransacaoConfig {

    @Bean
    TransacaoDtoMapper mapper(){
        return new TransacaoDtoMapper();
    }

    @Bean
    CriarTransacao criarTransacao(RepositorioDeTransacao repositorioDeTransacao,RepositorioProdutorDeTransacao repositorioProdutorDeTransacao){
        return new CriarTransacao(repositorioDeTransacao, repositorioProdutorDeTransacao);
    }

    @Bean
    ListarTransacao listarTransacao(RepositorioDeTransacao repositorioDeTransacao){
        return new ListarTransacao(repositorioDeTransacao);
    }

    @Bean
    RepositorioDeTransacaoJpa criarRepositorioJpa(TransacaoRepository repositorio, TransacaoEntityMapper mapper){
        return new RepositorioDeTransacaoJpa(repositorio, mapper);
    }

    @Bean
    TransacaoEntityMapper retornaMapper(){
        return new TransacaoEntityMapper();
    }

    @Bean
    BuscarTransacaoPorId buscarTransacaoPorId(RepositorioDeTransacao repositorioDeTransacao){
        return new BuscarTransacaoPorId(repositorioDeTransacao);
    }

    @Bean
    RepositorioDeTransacaoKafka publicarTransacao( KafkaTemplate<UUID, Transacao> kafkaTemplate){
        return new RepositorioDeTransacaoKafka(kafkaTemplate);
    }
}
