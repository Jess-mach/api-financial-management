package br.com.ntt.transacao.producer.application.gateways;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.UUID;

public interface RepositorioDeExportacao {
    ByteArrayInputStream gerarExcel(UUID usuarioId);

}
