package br.com.alura.codechella.config;

import br.com.alura.codechella.application.gateways.RepositorioDeTransacao;
import br.com.alura.codechella.application.usecases.CriarTransacao;
import br.com.alura.codechella.application.usecases.ListarTransacao;
import br.com.alura.codechella.infra.gateways.RepositorioDeTransacaoJpa;
import br.com.alura.codechella.infra.gateways.TransacaoEntityMapper;
import br.com.alura.codechella.infra.persistence.TransacaoRepository;
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
