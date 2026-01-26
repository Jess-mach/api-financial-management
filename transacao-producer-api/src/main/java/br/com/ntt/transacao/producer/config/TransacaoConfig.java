package br.com.ntt.transacao.producer.config;

import br.com.ntt.transacao.producer.application.gateways.RepositorioDeTransacao;
import br.com.ntt.transacao.producer.application.gateways.RepositorioDeExportacao;
import br.com.ntt.transacao.producer.application.gateways.RepositorioProdutorDeTransacao;
import br.com.ntt.transacao.producer.application.usecases.BuscarTransacaoPorId;
import br.com.ntt.transacao.producer.application.usecases.CriarTransacao;
import br.com.ntt.transacao.producer.application.usecases.ExportarTransacao;
import br.com.ntt.transacao.producer.application.usecases.ListarTransacao;
import br.com.ntt.transacao.producer.infra.gateways.RepositorioDeTransacaoKafka;
import br.com.ntt.transacao.producer.domain.entities.transacao.Transacao;
import br.com.ntt.transacao.producer.infra.controller.mapper.TransacaoDtoMapper;
import br.com.ntt.transacao.producer.infra.gateways.RepositorioDeTransacaoJpa;
import br.com.ntt.transacao.producer.infra.gateways.TransacaoEntityMapper;
import br.com.ntt.transacao.producer.infra.persistence.TransacaoRepository;
import br.com.ntt.transacao.producer.infra.gateways.RelatorioDeExportacaoTransacaoArquivo;
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

    @Bean
    RelatorioDeExportacaoTransacaoArquivo relatorioTransacaoService(RepositorioDeTransacao repositorioDeTransacao){
        return new RelatorioDeExportacaoTransacaoArquivo(repositorioDeTransacao);
    }

    @Bean
    ExportarTransacao gerarExcel(RepositorioDeExportacao repositorioDeExportacao){
        return new ExportarTransacao(repositorioDeExportacao);
    }

}
