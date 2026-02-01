package br.com.ntt.transacao.consumer.application.usecases;

import br.com.ntt.common.transacao.domain.entity.Transacao;
import br.com.ntt.common.transacao.domain.model.StatusTransacao;
import br.com.ntt.common.transacao.domain.model.TipoTransacao;
import br.com.ntt.transacao.consumer.application.gateways.RepositorioConversaoMoeda;
import br.com.ntt.transacao.consumer.application.gateways.RepositorioDeTransacao;
import br.com.ntt.transacao.consumer.application.gateways.RepositorioSaldoCliente;
import br.com.ntt.transacao.consumer.domain.entity.conta.SaldoConta;
import br.com.ntt.transacao.consumer.domain.entity.cotacao.Cotacao;
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
import java.util.List;
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
    void validacaoSaqueComSucesso() throws JsonProcessingException {
        Transacao transacao = criarTransacao(120.22, TipoTransacao.SAQUE, "BRL");

        when(repositorioSaldoCliente.buscarPorId(any())).thenReturn(criarsaldo(1700));

        when(repositorioConversaoMoeda.conversaoMoeda(any(), any())).thenReturn(criarConversorDeMoeda("BRL"));

        ArgumentCaptor<Transacao> capturaTransacaoJpa = ArgumentCaptor.forClass(Transacao.class);

        processarTransacao.executar(transacao);

        verify(repositorioJpa).atualizarTransacao(capturaTransacaoJpa.capture());

        Transacao transacaoSalva = capturaTransacaoJpa.getValue();

        verify(repositorioSaldoCliente, times(1)).atualizarSaldo(any(), any());

        assertEquals(StatusTransacao.AUTORIZADO, transacaoSalva.getStatus());
        assertEquals(BigDecimal.valueOf(120.22), transacaoSalva.getValor());
    }

    @Test
    void validacaoSaqueRejeitadaComMoedaEuro() throws JsonProcessingException {
        Transacao transacao = criarTransacao(500.0, TipoTransacao.SAQUE, "EUR");

        when(repositorioSaldoCliente.buscarPorId(any())).thenReturn(criarsaldo(200));

        when(repositorioConversaoMoeda.conversaoMoeda(any(), any())).thenReturn(criarConversorDeMoeda("EUR"));

        ArgumentCaptor<Transacao> capturaTransacaoJpa = ArgumentCaptor.forClass(Transacao.class);

        processarTransacao.executar(transacao);

        verify(repositorioJpa).atualizarTransacao(capturaTransacaoJpa.capture());

        Transacao transacaoSalva = capturaTransacaoJpa.getValue();

        verify(repositorioSaldoCliente, times(1)).atualizarSaldo(any(), any());

        assertEquals(StatusTransacao.REJEITADO, transacaoSalva.getStatus());
        assertEquals(BigDecimal.valueOf(500.0), transacaoSalva.getValor());
    }


    @Test
    void validacaoDepositoComSucesso() throws JsonProcessingException {
        Transacao transacao = criarTransacao(1500.50, TipoTransacao.DEPOSITO, "EUR");

        when(repositorioSaldoCliente.buscarPorId(any()))
                .thenReturn(criarsaldo(1700));

        when(repositorioConversaoMoeda.conversaoMoeda(any(), any()))
                .thenReturn(criarConversorDeMoeda("EUR"));

        ArgumentCaptor<Transacao> capturaTransacaoJpa = ArgumentCaptor.forClass(Transacao.class);

        processarTransacao.executar(transacao);

        verify(repositorioJpa).atualizarTransacao(capturaTransacaoJpa.capture());

        Transacao transacaoSalva = capturaTransacaoJpa.getValue();

        verify(repositorioSaldoCliente, times(1)).atualizarSaldo(any(), any());

        assertEquals(StatusTransacao.AUTORIZADO, transacaoSalva.getStatus());
        assertEquals(BigDecimal.valueOf(1500.50), transacaoSalva.getValor());
    }

    @Test
    void validacaoCompraComSucesso() throws JsonProcessingException {
        Transacao transacao = criarTransacao(800.70, TipoTransacao.COMPRA, "BRL");

        when(repositorioSaldoCliente.buscarPorId(any())).thenReturn(criarsaldo(1700));

        when(repositorioConversaoMoeda.conversaoMoeda(any(), any())).thenReturn(criarConversorDeMoeda("BRL"));

        ArgumentCaptor<Transacao> capturaTransacaoJpa = ArgumentCaptor.forClass(Transacao.class);

        processarTransacao.executar(transacao);

        verify(repositorioJpa).atualizarTransacao(capturaTransacaoJpa.capture());

        Transacao transacaoSalva = capturaTransacaoJpa.getValue();

        verify(repositorioSaldoCliente, times(1)).atualizarSaldo(any(), any());

        assertEquals(StatusTransacao.AUTORIZADO, transacaoSalva.getStatus());
        assertEquals(BigDecimal.valueOf(800.70), transacaoSalva.getValor());
    }

    @Test
    void validacaoCompraRejeitada() throws JsonProcessingException {
        Transacao transacao = criarTransacao(2000.0, TipoTransacao.COMPRA, "BRL");

        when(repositorioSaldoCliente.buscarPorId(any())).thenReturn(criarsaldo(1500));

        when(repositorioConversaoMoeda.conversaoMoeda(any(), any())).thenReturn(criarConversorDeMoeda("BRL"));

        ArgumentCaptor<Transacao> capturaTransacaoJpa = ArgumentCaptor.forClass(Transacao.class);

        processarTransacao.executar(transacao);

        verify(repositorioJpa).atualizarTransacao(capturaTransacaoJpa.capture());

        Transacao transacaoSalva = capturaTransacaoJpa.getValue();

        verify(repositorioSaldoCliente, times(1)).atualizarSaldo(any(), any());

        assertEquals(StatusTransacao.REJEITADO, transacaoSalva.getStatus());
        assertEquals(BigDecimal.valueOf(2000.0), transacaoSalva.getValor());
    }

    @Test
    void validacaoTranferenciaComSucesso() throws JsonProcessingException {
        Transacao transacao = criarTransacao(300.50, TipoTransacao.TRANSFERENCIA, "BRL");

        when(repositorioSaldoCliente.buscarPorId(any())).thenReturn(criarsaldo(1700));

        when(repositorioConversaoMoeda.conversaoMoeda(any(), any())).thenReturn(criarConversorDeMoeda("BRL"));

        ArgumentCaptor<Transacao> capturaTransacaoJpa = ArgumentCaptor.forClass(Transacao.class);

        processarTransacao.executar(transacao);

        verify(repositorioJpa).atualizarTransacao(capturaTransacaoJpa.capture());

        Transacao transacaoSalva = capturaTransacaoJpa.getValue();

        verify(repositorioSaldoCliente, times(1)).atualizarSaldo(any(), any());

        assertEquals(StatusTransacao.AUTORIZADO, transacaoSalva.getStatus());
        assertEquals(BigDecimal.valueOf(300.50), transacaoSalva.getValor());
    }

    @Test
    void validacaoTranferenciaRejeitada() throws JsonProcessingException {
        Transacao transacao = criarTransacao(2000.0, TipoTransacao.COMPRA, "BRL");

        when(repositorioSaldoCliente.buscarPorId(any())).thenReturn(criarsaldo(1500));

        when(repositorioConversaoMoeda.conversaoMoeda(any(), any())).thenReturn(criarConversorDeMoeda("BRL"));

        ArgumentCaptor<Transacao> capturaTransacaoJpa = ArgumentCaptor.forClass(Transacao.class);

        processarTransacao.executar(transacao);

        verify(repositorioJpa).atualizarTransacao(capturaTransacaoJpa.capture());

        Transacao transacaoSalva = capturaTransacaoJpa.getValue();

        verify(repositorioSaldoCliente, times(1)).atualizarSaldo(any(), any());

        assertEquals(StatusTransacao.REJEITADO, transacaoSalva.getStatus());
        assertEquals(BigDecimal.valueOf(2000.0), transacaoSalva.getValor());
    }

    @Test
    void validacaoDeSaidaEmDinheiroComSucesso() throws JsonProcessingException {
        Transacao transacao = criarTransacao(200.50, TipoTransacao.SAIDA_EM_DINHEIRO, "BRL");

        when(repositorioSaldoCliente.buscarPorId(any()))
                .thenReturn(criarsaldo(1700));

        when(repositorioConversaoMoeda.conversaoMoeda(any(), any()))
                .thenReturn(criarConversorDeMoeda("BRL"));

        ArgumentCaptor<Transacao> capturaTransacaoJpa = ArgumentCaptor.forClass(Transacao.class);

        processarTransacao.executar(transacao);

        verify(repositorioJpa).atualizarTransacao(capturaTransacaoJpa.capture());

        Transacao transacaoSalva = capturaTransacaoJpa.getValue();

        verify(repositorioSaldoCliente, times(1)).atualizarSaldo(any(), any());

        assertEquals(StatusTransacao.AUTORIZADO, transacaoSalva.getStatus());
        assertEquals(BigDecimal.valueOf(200.50), transacaoSalva.getValor());
    }

    @Test
    void validacaoDeSaidaEmDinheiroComSaldoInsuficienteDeveSerAutorizada() throws JsonProcessingException {
        Transacao transacao = criarTransacao(2000.0, TipoTransacao.SAIDA_EM_DINHEIRO, "BRL");

        when(repositorioSaldoCliente.buscarPorId(any()))
                .thenReturn(criarsaldo(1500));

        when(repositorioConversaoMoeda.conversaoMoeda(any(), any()))
                .thenReturn(criarConversorDeMoeda("BRL"));

        ArgumentCaptor<Transacao> capturaTransacaoJpa = ArgumentCaptor.forClass(Transacao.class);

        processarTransacao.executar(transacao);

        verify(repositorioJpa).atualizarTransacao(capturaTransacaoJpa.capture());

        Transacao transacaoSalva = capturaTransacaoJpa.getValue();

        verify(repositorioSaldoCliente, times(1)).atualizarSaldo(any(), any());

        // Para SAIDA_EM_DINHEIRO, a transação é autorizada mesmo com saldo insuficiente para análise posterior.
        assertEquals(StatusTransacao.AUTORIZADO, transacaoSalva.getStatus());
        assertEquals(BigDecimal.valueOf(2000.0), transacaoSalva.getValor());
    }

    @Test
    void validacaoTransferenciaRejeitadaComSaldoInsuficiente() throws JsonProcessingException {
        Transacao transacao = criarTransacao(2000.0, TipoTransacao.TRANSFERENCIA, "BRL");

        when(repositorioSaldoCliente.buscarPorId(any())).thenReturn(criarsaldo(1500));

        when(repositorioConversaoMoeda.conversaoMoeda(any(), any())).thenReturn(criarConversorDeMoeda("BRL"));

        ArgumentCaptor<Transacao> capturaTransacaoJpa = ArgumentCaptor.forClass(Transacao.class);

        processarTransacao.executar(transacao);

        verify(repositorioJpa).atualizarTransacao(capturaTransacaoJpa.capture());

        Transacao transacaoSalva = capturaTransacaoJpa.getValue();

        verify(repositorioSaldoCliente, times(1)).atualizarSaldo(any(), any());

        assertEquals(StatusTransacao.REJEITADO, transacaoSalva.getStatus());
        assertEquals(BigDecimal.valueOf(2000.0), transacaoSalva.getValor());
    }



    private static SaldoConta criarsaldo(int val) {
        return new SaldoConta(
                "Jessica",
                "1",
                BigDecimal.valueOf(val),
                null,
                BigDecimal.valueOf(val)
        );
    }

    private static ConversorMoeda criarConversorDeMoeda(String moeda) {
        return new ConversorMoeda(
                moeda,
                LocalDateTime.now().toString(),
                moeda.equals("BRL") ?
                        null :
                        List.of(
                                new Cotacao(
                                        BigDecimal.valueOf(6.20),
                                        BigDecimal.valueOf(6.20),
                                        BigDecimal.valueOf(6.20),
                                        BigDecimal.valueOf(6.20),
                                        "",
                                        ""

                                )
                        )
        );
    }

    private Transacao criarTransacao(Double valor, TipoTransacao tipo, String moeda) {
        return new Transacao(
                null,
                UUID.randomUUID(),
                BigDecimal.valueOf(valor),
                tipo,
                StatusTransacao.PENDENTE,
                LocalDateTime.now(),
                null,
                moeda,
                null,
                "Preciso pagar a cartão de crédito",
                1L,
                BigDecimal.valueOf(valor)
        );
    }
}