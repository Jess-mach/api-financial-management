package br.com.ntt.transacao.producer.application.usecases;

import br.com.ntt.common.transacao.domain.entity.Transacao;
import br.com.ntt.transacao.producer.application.gateways.RepositorioDeTransacao;
import br.com.ntt.transacao.producer.domain.Usuario;
import br.com.ntt.common.transacao.domain.exception.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ListarTransacao {

    private final RepositorioDeTransacao repositorio;

    public ListarTransacao(RepositorioDeTransacao repositorio) {
        this.repositorio = repositorio;
    }

    public List<Transacao> listarTodos(UUID usuarioId, Usuario usuarioLogado) {
        if (!usuarioLogado.perfilUsuario().equals("GERENTE") && usuarioId == null)
            throw new AccessDeniedException("Acesso negado");

        if (usuarioId != null && !usuarioId.equals(usuarioLogado.id()) && !usuarioLogado.perfilUsuario().equals("GERENTE"))
            throw new AccessDeniedException("Acesso negado");

        return this.repositorio.listarTodos(usuarioId);
    }

}

