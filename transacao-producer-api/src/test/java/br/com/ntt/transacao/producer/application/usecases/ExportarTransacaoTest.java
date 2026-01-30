package br.com.ntt.transacao.producer.application.usecases;

import br.com.ntt.transacao.producer.application.gateways.RepositorioDeExportacao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;

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

        ByteArrayInputStream resultado = exportarTransacao.gerarExcel(any(), usuarioLogado);

        assertNotNull(resultado);
        verify(repositorioDeExportacao, times(1)).gerarExcel(any());

    }
}