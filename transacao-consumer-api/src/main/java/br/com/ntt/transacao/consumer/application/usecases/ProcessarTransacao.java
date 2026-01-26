package br.com.ntt.transacao.consumer.application.usecases;

import br.com.ntt.transacao.consumer.application.gateways.RepositorioConversaoMoeda;
import br.com.ntt.transacao.consumer.application.gateways.RepositorioDeTransacao;
import br.com.ntt.transacao.consumer.application.gateways.RepositorioProdutorDeTransacao;
import br.com.ntt.transacao.consumer.application.gateways.RepositorioSaldoCliente;
import br.com.ntt.transacao.consumer.application.service.ValidadorDeTransacao;
import br.com.ntt.transacao.consumer.domain.entities.conta.SaldoConta;
import br.com.ntt.transacao.consumer.domain.entities.moeda.ConversorMoeda;
import br.com.ntt.transacao.consumer.domain.entities.transacao.Transacao;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadLocalRandom;

@Slf4j
public class ProcessarTransacao {

    private final RepositorioDeTransacao repositorio;

    private final RepositorioProdutorDeTransacao repositorioProdutorDeTransacao;

    private final RepositorioSaldoCliente repositorioSaldoCliente;

    private final RepositorioConversaoMoeda repositorioConversaoMoeda;

    private final ValidadorDeTransacao validadorDeTransacao;

    public ProcessarTransacao(RepositorioDeTransacao repositorio,
                              RepositorioProdutorDeTransacao repositorioProdutorDeTransacao,
                              RepositorioSaldoCliente repositorioSaldoCliente,
                              RepositorioConversaoMoeda repositorioConversaoMoeda,
                              ValidadorDeTransacao validadorDeTransacao) {
        this.repositorio = repositorio;
        this.repositorioProdutorDeTransacao = repositorioProdutorDeTransacao;
        this.repositorioSaldoCliente = repositorioSaldoCliente;
        this.repositorioConversaoMoeda = repositorioConversaoMoeda;
        this.validadorDeTransacao = validadorDeTransacao;
    }

    public Transacao executar(Transacao transacao) throws JsonProcessingException {
        Transacao transacaoSalva = null;

        SaldoConta saldoConta = repositorioSaldoCliente.buscarPorId(getValidAccountId());
        ConversorMoeda conversorMoeda = repositorioConversaoMoeda.conversaoMoeda(transacao.getMoeda(), transacao.getDataHoraSolicitacao());

        Transacao transacaoValidada = validadorDeTransacao.validarTransacao(transacao, saldoConta, conversorMoeda);

        transacaoSalva = repositorio.atualizarTransacao(transacaoValidada);

        repositorioSaldoCliente.atualizarSaldo(saldoConta, transacao);

        return transacaoSalva;
    }

    public Long getValidAccountId() {
        return ThreadLocalRandom.current().nextLong(1, 51);
    }
}


