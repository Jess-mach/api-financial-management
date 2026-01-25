package br.com.ntt.transacao.consumer.config;

import br.com.ntt.transacao.consumer.application.gateways.RepositorioSaldoCliente;
import br.com.ntt.transacao.consumer.application.gateways.RepositorioDeTransacao;
import br.com.ntt.transacao.consumer.application.gateways.RepositorioProdutorDeTransacao;
import br.com.ntt.transacao.consumer.application.usecases.ProcessarTransacao;
import br.com.ntt.transacao.consumer.infra.gateways.RepositorioDeTransacaoKafka;
import br.com.ntt.transacao.consumer.domain.entities.transacao.Transacao;
import br.com.ntt.transacao.consumer.infra.consumer.mapper.TransacaoDtoMapper;
import br.com.ntt.transacao.consumer.infra.gateways.RepositorioDeTransacaoJpa;
import br.com.ntt.transacao.consumer.infra.gateways.TransacaoEntityMapper;
import br.com.ntt.transacao.consumer.infra.persistence.TransacaoRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.UUID;

@Configuration
public class TransacaoConfig {

    @Value(value = "${endpoint.consulta.saldo:}")
    private String endpointConsultaSaldo;

    @Bean
    TransacaoDtoMapper mapper(){
        return new TransacaoDtoMapper();
    }

    @Bean
    ProcessarTransacao criarTransacao(RepositorioDeTransacao repositorioDeTransacao,
                                      RepositorioProdutorDeTransacao repositorioProdutorDeTransacao,
                                      RepositorioSaldoCliente repositorioSaldoCliente){
        return new ProcessarTransacao(repositorioDeTransacao, repositorioProdutorDeTransacao, repositorioSaldoCliente);
    }



    @Bean
    RepositorioDeTransacaoJpa criarRepositorioJpa(TransacaoRepository repositorio, TransacaoEntityMapper mapper){
        return new RepositorioDeTransacaoJpa(repositorio, mapper);
    }

    @Bean
    TransacaoEntityMapper retornaMapper(){
        return new TransacaoEntityMapper();
    }
//
//    @Bean
//    BuscarTransacaoPorId buscarTransacaoPorId(RepositorioDeTransacao repositorioDeTransacao){
//        return new BuscarTransacaoPorId(repositorioDeTransacao);
//    }

    @Bean
    RepositorioDeTransacaoKafka publicarTransacao( KafkaTemplate<UUID, Transacao> kafkaTemplate){
        return new RepositorioDeTransacaoKafka(kafkaTemplate);
    }

//    @Bean
//    RepositorioContaClienteHttp buscarPorId(String endpointConsultaSaldo){
//        return new RepositorioContaClienteHttp(endpointConsultaSaldo);
//    }
}
