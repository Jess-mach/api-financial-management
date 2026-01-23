package br.com.ntt.transacao.producer.config;

import br.com.ntt.transacao.producer.application.gateways.RepositorioDeTransacao;
import br.com.ntt.transacao.producer.application.usecases.CriarTransacao;
import br.com.ntt.transacao.producer.application.usecases.ListarTransacao;
import br.com.ntt.transacao.producer.infra.gateways.RepositorioDeTransacaoJpa;
import br.com.ntt.transacao.producer.infra.gateways.TransacaoEntityMapper;
import br.com.ntt.transacao.producer.infra.persistence.TransacaoRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransacaoConfig {

    @Bean
    CriarTransacao criarUsuario(RepositorioDeTransacao repositorioDeTransacao){
        return new CriarTransacao(repositorioDeTransacao);
    }

    @Bean
    ListarTransacao listarUsuarios(RepositorioDeTransacao repositorioDeTransacao){
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
}
