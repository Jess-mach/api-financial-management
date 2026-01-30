package br.com.ntt.transacao.producer.infra.gateways;

import br.com.ntt.common.transacao.domain.entity.Transacao;
import br.com.ntt.common.transacao.domain.exception.BusinessException;
import br.com.ntt.common.transacao.domain.model.StatusTransacao;
import br.com.ntt.common.transacao.domain.model.TipoTransacao;
import br.com.ntt.transacao.producer.application.gateways.RepositorioDeTransacao;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RelatorioDeExportacaoTransacaoArquivoTest {

    @InjectMocks
    private RelatorioDeExportacaoTransacaoArquivo relatorioDeExportacaoTransacaoArquivo;

    @Mock
    private RepositorioDeTransacao repositorioDeTransacao;

    @Mock
    private Appender<ILoggingEvent> mockAppender;

    @Captor
    private ArgumentCaptor<ILoggingEvent> captorLoggingEvent;

    @Test
    @DisplayName("Deve exportar os dados em excel")
    void deveExportarDadosExcelComSucesso() {

        Transacao transacao = new Transacao(
                UUID.randomUUID(),
                UUID.randomUUID(),
                new BigDecimal("150.00"),
                TipoTransacao.DEPOSITO,
                StatusTransacao.PENDENTE,
                LocalDateTime.now(),
                null,
                "BRL",
                BigDecimal.ONE,
                "Teste de transação",
                123456L,
                new BigDecimal("150.00")
        );

        when(repositorioDeTransacao.listarTodos(any())).thenReturn(List.of(transacao));

        Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        logger.addAppender(mockAppender);

        ByteArrayInputStream analise = relatorioDeExportacaoTransacaoArquivo.gerarExcel(UUID.randomUUID());

        assertNotNull(analise);
        verify(mockAppender, atLeastOnce()).doAppend(captorLoggingEvent.capture());

        ILoggingEvent event = captorLoggingEvent.getValue();
        assertEquals(Level.INFO, event.getLevel());
        assertEquals("excel gerado com sucesso", event.getFormattedMessage());

        verify(repositorioDeTransacao, never()).visualizarGastosDia(any());
        verify(repositorioDeTransacao, times(1)).listarTodos(any());
    }

    @Test
    @DisplayName("Deve apresentar erro ao tentar exportar os dados em excel")
    void naoDeveExportarDadosExcel() {

        Transacao transacao = new Transacao(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );

        when(repositorioDeTransacao.listarTodos(any())).thenReturn(List.of(transacao));

        Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        logger.addAppender(mockAppender);

        assertThrows(BusinessException.class, () -> relatorioDeExportacaoTransacaoArquivo.gerarExcel(UUID.randomUUID()));

        verify(mockAppender, atLeastOnce()).doAppend(captorLoggingEvent.capture());

        ILoggingEvent event = captorLoggingEvent.getValue();
        assertEquals(Level.INFO, event.getLevel());
        assertEquals("Falha ao gerar o excel", event.getFormattedMessage());

        verify(repositorioDeTransacao, never()).visualizarGastosDia(any());
        verify(repositorioDeTransacao, times(1)).listarTodos(any());
    }
}