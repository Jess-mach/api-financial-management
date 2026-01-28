package br.com.ntt.transacao.producer.application.usecases;

import br.com.ntt.transacao.producer.application.gateways.RepositorioDeTransacao;
import br.com.ntt.transacao.producer.domain.entity.Transacao;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListarTransacao {

    private final RepositorioDeTransacao repositorio;

    public ListarTransacao(RepositorioDeTransacao repositorio) {
        this.repositorio = repositorio;
    }

    public List<Transacao> listarTodos(){
        return this.repositorio.listarTodos();
    }

}

