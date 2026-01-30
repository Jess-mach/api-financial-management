package br.com.ntt.transacao.producer.application.usecases;

import br.com.ntt.common.transacao.domain.entity.Transacao;
import br.com.ntt.common.transacao.domain.model.StatusTransacao;
import br.com.ntt.common.transacao.domain.model.TipoTransacao;
import br.com.ntt.transacao.producer.application.gateways.RepositorioConsultaUsuario;
import br.com.ntt.transacao.producer.application.gateways.RepositorioDeTransacao;
import br.com.ntt.transacao.producer.application.gateways.RepositorioProdutorDeTransacao;
import br.com.ntt.common.transacao.domain.exception.ResourceNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CriarTransacaoTest {

    @Mock
    private RepositorioDeTransacao repositorio;

    @Mock
    private RepositorioProdutorDeTransacao repositorioProdutorDeTransacao;

    @Mock
    private RepositorioConsultaUsuario repositorioConsultaUsuario;

    @InjectMocks
    private CriarTransacao criarTransacao;

    @Test
    @DisplayName("Deve salvar transação e publicar no Kafka com sucesso")
    void deveSalvarEPublicarTransacaoComSucesso() {
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
        when(repositorio.cadastrarTransacao(any(Transacao.class), any(String.class))).thenReturn(transacao);

        Transacao resultado = criarTransacao.executar(transacao, "token");

        assertNotNull(resultado);
        assertEquals(transacao.getId(), resultado.getId());

        verify(repositorio, times(1)).cadastrarTransacao(transacao, "token");

        verify(repositorioProdutorDeTransacao, times(1)).publicarTransacao(transacao);
    }

    @Test
    @DisplayName("Não deve publicar no Kafka se houver erro ao salvar no banco de dados")
    void naoDevePublicarSeErroNoRepositorio() {
        Transacao transacaoInput = new Transacao(
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
        when(repositorio.cadastrarTransacao(any(Transacao.class), any(String.class)))
                .thenThrow(new ResourceNotFoundException("Erro ao conectar no Postgres"));

        assertThrows(ResourceNotFoundException.class, () -> criarTransacao.executar(transacaoInput, "token"));

        verify(repositorioProdutorDeTransacao, never()).publicarTransacao(any());
    }
}