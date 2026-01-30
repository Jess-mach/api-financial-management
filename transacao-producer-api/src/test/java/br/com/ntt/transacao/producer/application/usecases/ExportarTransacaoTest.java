package br.com.ntt.transacao.producer.application.usecases;

import br.com.ntt.transacao.producer.application.gateways.RepositorioDeExportacao;
import br.com.ntt.transacao.producer.domain.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExportarTransacaoTest {

    @InjectMocks
    private ExportarTransacao exportarTransacao;

    @Mock
    private RepositorioDeExportacao repositorioDeExportacao;

    @Test
    @DisplayName("Deve gerar planilha de transações com sucesso")
    void deveGerarExelComSucesso (){

        when(repositorioDeExportacao.gerarExcel(any()))
                .thenReturn(new ByteArrayInputStream("valor teste".getBytes()));

        UUID usuarioId = UUID.randomUUID();
        Usuario administrador = new Usuario(
                usuarioId,
                null,
                "ADMINISTRADOR"
        );
        ByteArrayInputStream resultado = exportarTransacao.gerarExcel(usuarioId, administrador);

        assertNotNull(resultado);
        verify(repositorioDeExportacao, times(1)).gerarExcel(any());

    }
}