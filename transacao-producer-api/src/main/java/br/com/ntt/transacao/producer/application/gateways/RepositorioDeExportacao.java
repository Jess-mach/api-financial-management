package br.com.ntt.transacao.producer.application.gateways;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public interface RepositorioDeExportacao {
    ByteArrayInputStream gerarExcel() throws IOException;

}
