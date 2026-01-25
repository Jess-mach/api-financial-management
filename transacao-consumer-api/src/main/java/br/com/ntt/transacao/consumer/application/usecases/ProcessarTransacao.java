package br.com.ntt.transacao.consumer.application.usecases;

import br.com.ntt.transacao.consumer.application.gateways.RepositorioConversaoMoeda;
import br.com.ntt.transacao.consumer.application.gateways.RepositorioSaldoCliente;
import br.com.ntt.transacao.consumer.application.gateways.RepositorioDeTransacao;
import br.com.ntt.transacao.consumer.application.gateways.RepositorioProdutorDeTransacao;
import br.com.ntt.transacao.consumer.domain.entities.conta.SaldoConta;
import br.com.ntt.transacao.consumer.domain.entities.moeda.ConversorMoeda;
import br.com.ntt.transacao.consumer.domain.entities.transacao.Transacao;
import br.com.ntt.transacao.consumer.domain.model.StatusTransacao;
import br.com.ntt.transacao.consumer.domain.model.TipoTransacao;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
public class ProcessarTransacao {

    private final RepositorioDeTransacao repositorio;

    private final RepositorioProdutorDeTransacao repositorioProdutorDeTransacao;

    private final RepositorioSaldoCliente repositorioSaldoCliente;

    private final RepositorioConversaoMoeda repositorioConversaoMoeda;

    public ProcessarTransacao(RepositorioDeTransacao repositorio, RepositorioProdutorDeTransacao repositorioProdutorDeTransacao, RepositorioSaldoCliente repositorioSaldoCliente, RepositorioConversaoMoeda repositorioConversaoMoeda) {
        this.repositorio = repositorio;
        this.repositorioProdutorDeTransacao = repositorioProdutorDeTransacao;
        this.repositorioSaldoCliente = repositorioSaldoCliente;
        this.repositorioConversaoMoeda = repositorioConversaoMoeda;
    }

    public Transacao executar(Transacao transacao) {
        Transacao transacaoSalva = null;
        try {
            //Passar fixo o id da conta pra testar
            //1 - **Integração com Mock API:** Consulta a API externa (MockAPI) para validar saldo, conta e limites do usuário.
            //**Regras de Negócio Complexas:** Aplica as validações de saldo suficiente e limites.

            /*
            Dentro de infra, você pode criar um pacote para gateways ou clients para organizar melhor. Por exemplo:
br.com.ntt.transacao.consumer.infra.gateways.http ou br.com.ntt.transacao.consumer.infra.clients.http

             */
            //2 - **Integração com Câmbio:** Consome a API pública (Brasil API) para converter valores e registrar a taxa de câmbio da operação.

            /*
            * https://brasilapi.com.br/api/cambio/v1/cotacao/EUR/2025-11-01
            * Salvar a taxa de Cambio - Deposito EUR -
            *
            * */


            //3 - **Atualização de Status:** Atualiza a transação no banco para `APPROVED` ou `REJECTED` com os detalhes.

            // transacao.valor x taxaCambio ee saldo > transacao. valor  transacao = APPROVADE else REJECTE
            SaldoConta saldoConta = repositorioSaldoCliente.buscarPorId(getValidAccountId());
            ConversorMoeda conversorMoeda  = repositorioConversaoMoeda.conversaoMoeda(transacao.getMoeda(), transacao.getDataHoraSolicitacao());

            BigDecimal valorDoSaldo = saldoConta.getSaldo();
            BigDecimal valorDaTransacao = transacao.getValor();
            BigDecimal valorTaxaDeCambio = conversorMoeda.getCotacoes().get(0).getCotacaoVenda();

            if(transacao.getTipo().equals(TipoTransacao.DEPOSITO)){
                if(!transacao.getMoeda().equals("BRL")){
                    transacao.atualizaTaxaDeCambio(valorTaxaDeCambio);
                }

                if(valorTaxaDeCambio > 0)
                    valorDaTransacao = valorDaTransacao * valorTaxaDeCambio;

                transacao.atualizaStatus(StatusTransacao.AUTORIZADO);
            }

            transacao.atualizaStatus(StatusTransacao.REJEITADO);


            transacaoSalva = repositorio.atualizarTransacao(transacao);
        } catch (Exception e) {
            //**Tratamento de Erros (DLQ):** Gerencia erros irrecuperáveis enviando para um tópico de Dead Letter Queue (`transaction.dlq`).

            repositorioProdutorDeTransacao.publicarTransacao(transacao); //TODO no topico de DLQ
        }
        return transacaoSalva;
    }

    public Long getValidAccountId() {
        return ThreadLocalRandom.current().nextLong(1, 51);
    }
}


