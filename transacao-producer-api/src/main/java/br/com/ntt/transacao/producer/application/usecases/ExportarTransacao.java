package br.com.ntt.transacao.producer.application.usecases;

import br.com.ntt.transacao.producer.application.gateways.RepositorioDeExportacao;
import br.com.ntt.transacao.producer.domain.Usuario;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.util.UUID;

@Component
public class ExportarTransacao {
    private final RepositorioDeExportacao repositorioDeExportacao;

    public ExportarTransacao(RepositorioDeExportacao repositorioDeExportacao) {
        this.repositorioDeExportacao = repositorioDeExportacao;
    }

    public ByteArrayInputStream gerarExcel(UUID usuarioId, Usuario usuarioLogado) {
        if (usuarioId.equals(usuarioLogado.id()))
            return repositorioDeExportacao.gerarExcel(usuarioId);

        if (usuarioLogado.perfilUsuario().equals("GERENTE"))
            return repositorioDeExportacao.gerarExcel(usuarioId);

        throw new AccessDeniedException("Acesso negado");
    }
}
