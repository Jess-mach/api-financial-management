package br.com.ntt.transacao.consumer.application.usecases;

import br.com.ntt.common.transacao.domain.entity.Transacao;
import br.com.ntt.common.transacao.domain.model.StatusTransacao;
import br.com.ntt.common.transacao.domain.model.TipoTransacao;
import br.com.ntt.transacao.consumer.application.gateways.RepositorioConversaoMoeda;
import br.com.ntt.transacao.consumer.application.gateways.RepositorioDeTransacao;
import br.com.ntt.transacao.consumer.application.gateways.RepositorioSaldoCliente;
import br.com.ntt.transacao.consumer.domain.entity.conta.SaldoConta;
import br.com.ntt.transacao.consumer.domain.entity.moeda.ConversorMoeda;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProcessarTransacaoTest {

    @InjectMocks
    private ProcessarTransacao processarTransacao;

    @Mock
    private RepositorioDeTransacao repositorioJpa;

    @Mock
    private RepositorioSaldoCliente repositorioSaldoCliente;

    @Mock
    private RepositorioConversaoMoeda repositorioConversaoMoeda;

    private ValidadorDeTransacao validadorDeTransacao = new ValidadorDeTransacao();

    @BeforeEach
    void setUp() {
        processarTransacao = new ProcessarTransacao(
                repositorioJpa,
                repositorioSaldoCliente,
                repositorioConversaoMoeda,
                validadorDeTransacao
        );
    }

    @Test
    void validacaoDepositoComSucesso() throws JsonProcessingException {
        Transacao transacao = new Transacao(
                null,
                UUID.randomUUID(),
                BigDecimal.valueOf(120.22),
                TipoTransacao.SAQUE,
                StatusTransacao.PENDENTE,
                LocalDateTime.now(),
                null,
                "BRL",
                null,
                "Preciso pagar a conta de agua",
                1L
        );

        when(repositorioSaldoCliente.buscarPorId(any()))
                .thenReturn(new SaldoConta(
                        "Jessica",
                        "1",
                        BigDecimal.valueOf(1700),
                        null,
                        BigDecimal.valueOf(15)
                ));

        when(repositorioConversaoMoeda.conversaoMoeda(any(), any()))
                .thenReturn(
                        new ConversorMoeda(
                                "BRL",
                                LocalDateTime.now().toString(),
                                null
                        )
                );

        ArgumentCaptor<Transacao> capturaTransacaoJpa = ArgumentCaptor.forClass(Transacao.class);

        processarTransacao.executar(transacao);

        verify(repositorioJpa).atualizarTransacao(capturaTransacaoJpa.capture());

        Transacao transacaoSalva = capturaTransacaoJpa.getValue();

        verify(repositorioSaldoCliente, times(1)).atualizarSaldo(any(), any());

        assertEquals(StatusTransacao.AUTORIZADO, transacaoSalva.getStatus());
        assertEquals(BigDecimal.valueOf(120.22), transacaoSalva.getValor());
    }
}