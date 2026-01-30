package br.com.ntt.transacao.producer.application.usecases;

import br.com.ntt.common.transacao.domain.entity.Transacao;
import br.com.ntt.common.transacao.domain.exception.BusinessException;
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
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListarTransacaoTest {
    @Mock
    private RepositorioDeTransacao repositorio;

    @InjectMocks
    private ListarTransacao listarTransacao;

    @Test
    @DisplayName("Sucesso na consulta")
    void deveConsultarComSucesso() {
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

        when(repositorio.listarTodos(any())).thenReturn(List.of(transacao));

        List<Transacao> resultado = listarTransacao.listarTodos(UUID.randomUUID());

        assertNotNull(resultado);
        verify(repositorio, times(1)).listarTodos(any());

    }

    @Test
    @DisplayName("Erro ao consultar no banco de dados")
    void erroConsultaTransacao() {
        when(repositorio.listarTodos(any()))
                .thenThrow(new BusinessException("Erro ao conectar no Postgres"));

        assertThrows(RuntimeException.class, () -> listarTransacao.listarTodos(UUID.randomUUID()));

    }
}