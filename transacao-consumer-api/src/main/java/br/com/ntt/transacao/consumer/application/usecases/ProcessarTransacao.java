package br.com.ntt.transacao.consumer.application.usecases;

import br.com.ntt.transacao.consumer.application.gateways.RepositorioConversaoMoeda;
import br.com.ntt.transacao.consumer.application.gateways.RepositorioDeTransacao;
import br.com.ntt.transacao.consumer.application.gateways.RepositorioSaldoCliente;
import br.com.ntt.transacao.consumer.application.service.ValidadorDeTransacao;
import br.com.ntt.transacao.consumer.domain.entities.conta.SaldoConta;
import br.com.ntt.transacao.consumer.domain.entities.moeda.ConversorMoeda;
import br.com.ntt.transacao.consumer.domain.entities.transacao.Transacao;
import br.com.ntt.transacao.consumer.domain.model.StatusTransacao;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ThreadLocalRandom;

@Slf4j
public class ProcessarTransacao {

    private final RepositorioDeTransacao repositorio;


    private final RepositorioSaldoCliente repositorioSaldoCliente;

    private final RepositorioConversaoMoeda repositorioConversaoMoeda;

    private final ValidadorDeTransacao validadorDeTransacao;

    public ProcessarTransacao(RepositorioDeTransacao repositorio,
                              RepositorioSaldoCliente repositorioSaldoCliente,
                              RepositorioConversaoMoeda repositorioConversaoMoeda,
                              ValidadorDeTransacao validadorDeTransacao) {
        this.repositorio = repositorio;
        this.repositorioSaldoCliente = repositorioSaldoCliente;
        this.repositorioConversaoMoeda = repositorioConversaoMoeda;
        this.validadorDeTransacao = validadorDeTransacao;
    }

    @Transactional
    public Transacao executar(Transacao transacao) throws JsonProcessingException {
        SaldoConta saldoConta = repositorioSaldoCliente.buscarPorId(getValidAccountId());

        ConversorMoeda conversorMoeda = repositorioConversaoMoeda.conversaoMoeda(transacao.getMoeda(), transacao.getDataHoraSolicitacao());

        Transacao transacaoValidada = validadorDeTransacao.validarTransacao(transacao, saldoConta, conversorMoeda);

        Transacao transacaoSalva = repositorio.atualizarTransacao(transacaoValidada);

        repositorioSaldoCliente.atualizarSaldo(saldoConta, transacao);

        return transacaoSalva;
    }

    public Long getValidAccountId() {
        return ThreadLocalRandom.current().nextLong(1, 51);
    }

    public void atualizarStatusErro(Transacao transacao) {
        transacao.atualizaStatus(StatusTransacao.ERRO_PROCESSAMENTO);

        repositorio.atualizarTransacao(transacao);
    }
}


