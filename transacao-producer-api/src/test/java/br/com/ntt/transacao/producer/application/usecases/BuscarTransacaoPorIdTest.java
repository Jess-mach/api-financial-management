package br.com.ntt.transacao.producer.application.usecases;

import br.com.ntt.common.transacao.domain.entity.Transacao;
import br.com.ntt.common.transacao.domain.model.StatusTransacao;
import br.com.ntt.common.transacao.domain.model.TipoTransacao;
import br.com.ntt.transacao.producer.application.gateways.RepositorioDeTransacao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BuscarTransacaoPorIdTest {

    @Mock
    private RepositorioDeTransacao repositorio;

    @InjectMocks
    private BuscarTransacaoPorId buscarTransacaoPorId;

    @Test
    void deveRetornarTransacaoPorId() {
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
                123456L
        );
        when(repositorio.buscarPorId(any(UUID.class))).thenReturn(transacao);

        Transacao resultado = buscarTransacaoPorId.buscarPorId(UUID.randomUUID());

        assertEquals(transacao, resultado);
    }

    @Test
    @DisplayName("Transacao nao encontrado")
    void erroAoBuscarTransacao() {

        when(repositorio.buscarPorId(any())).thenThrow(new RuntimeException("Erro ao conectar no Postgres"));

        assertThrows(RuntimeException.class, () -> buscarTransacaoPorId.buscarPorId(UUID.randomUUID()));


    }

}