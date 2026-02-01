package br.com.ntt.transacao.producer.infra.controller;

import br.com.ntt.transacao.producer.application.usecases.ExportarTransacao;
import br.com.ntt.transacao.producer.domain.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayInputStream;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExportacaoTransacaoControllerTest {

    @Mock
    private ExportarTransacao exportarTransacao;

    @InjectMocks
    private ExportacaoTransacaoController controller;

    private Usuario usuarioLogado;
    private UUID usuarioId;

    @BeforeEach
    void setUp() {
        usuarioId = UUID.randomUUID();
        usuarioLogado = new Usuario(usuarioId, "John Doe", "CLIENTE");
    }

    @Test
    @DisplayName("Deve retornar arquivo Excel com sucesso")
    void deveRetornarExcelComSucesso() {
        // Arrange
        byte[] excelContent = "dummy excel content".getBytes();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(excelContent);
        when(exportarTransacao.gerarExcel(eq(usuarioId), eq(usuarioLogado))).thenReturn(inputStream);

        // Act
        ResponseEntity<Resource> response = controller.baixarExcel(usuarioId, usuarioLogado);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"), response.getHeaders().getContentType());
        assertEquals("attachment; filename=relatorio-transacoes.xlsx", response.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION));
        assertNotNull(response.getBody());
    }
}
