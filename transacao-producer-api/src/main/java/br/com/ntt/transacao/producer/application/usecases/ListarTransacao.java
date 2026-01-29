package br.com.ntt.transacao.producer.application.usecases;

import br.com.ntt.common.transacao.domain.entity.Transacao;
import br.com.ntt.transacao.producer.application.gateways.RepositorioDeTransacao;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ListarTransacao {

    private final RepositorioDeTransacao repositorio;

    public ListarTransacao(RepositorioDeTransacao repositorio) {
        this.repositorio = repositorio;
    }

    public List<Transacao> listarTodos(UUID usuarioId){
        return this.repositorio.listarTodos(usuarioId);
    }

}

