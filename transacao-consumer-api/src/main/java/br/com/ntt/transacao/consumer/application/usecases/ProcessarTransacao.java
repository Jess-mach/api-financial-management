package br.com.ntt.transacao.consumer.application.usecases;

import br.com.ntt.transacao.consumer.application.gateways.RepositorioSaldoCliente;
import br.com.ntt.transacao.consumer.application.gateways.RepositorioDeTransacao;
import br.com.ntt.transacao.consumer.application.gateways.RepositorioProdutorDeTransacao;
import br.com.ntt.transacao.consumer.domain.entities.SaldoConta;
import br.com.ntt.transacao.consumer.domain.entities.transacao.Transacao;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadLocalRandom;

@Slf4j
public class ProcessarTransacao {

    private final RepositorioDeTransacao repositorio;

    private final RepositorioProdutorDeTransacao repositorioProdutorDeTransacao;

    private final RepositorioSaldoCliente repositorioSaldoCliente;

    public ProcessarTransacao(RepositorioDeTransacao repositorio, RepositorioProdutorDeTransacao repositorioProdutorDeTransacao, RepositorioSaldoCliente repositorioSaldoCliente) {
        this.repositorio = repositorio;
        this.repositorioProdutorDeTransacao = repositorioProdutorDeTransacao;
        this.repositorioSaldoCliente = repositorioSaldoCliente;
    }

    public Transacao executar(Transacao transacao) {
        Transacao transacaoSalva = null;
        try {
            //Passar fixo o id da conta pra testar
            //1 - **Integração com Mock API:** Consulta a API externa (MockAPI) para validar saldo, conta e limites do usuário.
            //**Regras de Negócio Complexas:** Aplica as validações de saldo suficiente e limites.
            SaldoConta saldoConta = repositorioSaldoCliente.buscarPorId(getValidAccountId());

            /*
            Realizar uma chamada  para  https://6974ba31265838bbea95be42.mockapi.io/api/becca/v1/saldo

            usando  HttpClient ... => Response
            Capturar o retorno para processar no passo 3

            RepositorioDeConsultaDeSaldo
            Dentro de infra, você pode criar um pacote para gateways ou clients para organizar melhor. Por exemplo:
br.com.ntt.transacao.consumer.infra.gateways.http ou br.com.ntt.transacao.consumer.infra.clients.http

             */





            //2 - **Integração com Câmbio:** Consome a API pública (Brasil API) para converter valores e registrar a taxa de câmbio da operação.

            /*

            RepositorioDeConsultaDeTaxaDeCambio
            * https://brasilapi.com.br/api/cambio/v1/cotacao/EUR/2025-11-01
            * Salvar a taxa de Cambio - Deposito EUR -
            *
            * */


            //3 - **Atualização de Status:** Atualiza a transação no banco para `APPROVED` ou `REJECTED` com os detalhes.


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


