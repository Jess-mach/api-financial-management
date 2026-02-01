package br.com.ntt.transacao.producer.application.usecases;

import br.com.ntt.common.transacao.domain.entity.Transacao;
import br.com.ntt.common.transacao.domain.model.StatusTransacao;
import br.com.ntt.common.transacao.domain.model.TipoTransacao;
import br.com.ntt.transacao.producer.application.gateways.RepositorioDeTransacao;
import br.com.ntt.common.transacao.domain.exception.ResourceNotFoundException;
import br.com.ntt.transacao.producer.domain.Usuario;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BuscarTransacaoPorIdTest {

    @Mock
    private RepositorioDeTransacao repositorio;

    @InjectMocks
    private BuscarTransacaoPorId buscarTransacaoPorId;

    private UUID usuarioId = UUID.randomUUID();
    private Usuario usuarioLogado = new Usuario(
            usuarioId,
            null,
            "ADMINISTRADOR"
    );

    @Test
    @DisplayName("Busca de Transacao por Id com sucesso")
    void deveRetornarTransacaoPorId() {
        UUID idBusca = UUID.randomUUID();
        Transacao transacao = new Transacao(
                idBusca,
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
        when(repositorio.buscarPorId(eq(idBusca), eq(usuarioLogado))).thenReturn(transacao);

        Transacao resultado = buscarTransacaoPorId.buscarPorId(idBusca, usuarioLogado);

        assertEquals(transacao, resultado);
    }

    @Test
    @DisplayName("Transacao nao encontrado")
    void erroAoBuscarTransacao() {
        UUID idBusca = UUID.randomUUID();

        when(repositorio.buscarPorId(idBusca, usuarioLogado))
                .thenThrow(new ResourceNotFoundException("Erro ao conectar no Postgres"));

        assertThrows(ResourceNotFoundException.class,
                () -> buscarTransacaoPorId.buscarPorId(idBusca, usuarioLogado));


    }

}