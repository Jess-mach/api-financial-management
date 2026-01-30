package br.com.ntt.transacao.producer.application.usecases;

import br.com.ntt.common.transacao.domain.entity.Transacao;
import br.com.ntt.transacao.producer.application.gateways.RepositorioDeTransacao;
import br.com.ntt.transacao.producer.domain.Usuario;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ListarTransacao {

    private final RepositorioDeTransacao repositorio;

    public ListarTransacao(RepositorioDeTransacao repositorio) {
        this.repositorio = repositorio;
    }

    public List<Transacao> listarTodos(UUID usuarioId, Usuario usuarioLogado){
        if (!usuarioId.equals(usuarioLogado.id()) && !usuarioLogado.perfilUsuario().equals("GERENTE"))
            throw new AccessDeniedException("Acesso negado");

        return this.repositorio.listarTodos(usuarioId);
    }

}

