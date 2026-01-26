package br.com.ntt.transacao.producer.application.usecases;

import br.com.ntt.transacao.producer.application.gateways.RepositorioDeExportacao;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class ExportarTransacao {
    private final RepositorioDeExportacao repositorioDeExportacao;

    public ExportarTransacao(RepositorioDeExportacao repositorioDeExportacao) {
        this.repositorioDeExportacao = repositorioDeExportacao;
    }

    public ByteArrayInputStream gerarExcel() throws IOException {
        return repositorioDeExportacao.gerarExcel();
    }
}
