package br.com.ntt.transacao.consumer.application.usecases;

import br.com.ntt.common.transacao.domain.entity.Transacao;
import br.com.ntt.common.transacao.domain.model.StatusTransacao;
import br.com.ntt.transacao.consumer.domain.entity.conta.SaldoConta;
import br.com.ntt.transacao.consumer.domain.entity.moeda.ConversorMoeda;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ValidadorDeTransacao {

    public Transacao validarTransacao(Transacao transacao, SaldoConta saldoConta, ConversorMoeda conversorMoeda) {
        Double valorDoSaldo = saldoConta.getSaldo().doubleValue();
        Double valorDaTransacao = transacao.getValor().doubleValue();
        Double valorTaxaDeCambio = conversorMoeda.getCotacoes().get(0).getCotacaoVenda().doubleValue();
        Double valorLimiteCartao =  saldoConta.getLimiteCartao().doubleValue();

        if (!transacao.getMoeda().equals("BRL")) {
            transacao.setTaxaCambio(BigDecimal.valueOf(valorTaxaDeCambio));
        }

        switch (transacao.getTipo()) {
            case SAQUE:
                if (valorTaxaDeCambio > 0) {
                    valorDaTransacao = valorDaTransacao * valorTaxaDeCambio;
                }
                if (valorDaTransacao <= valorDoSaldo)
                    transacao.setStatus(StatusTransacao.AUTORIZADO);
                else
                    transacao.setStatus(StatusTransacao.REJEITADO);
                break;

            case DEPOSITO:
                transacao.setStatus(StatusTransacao.AUTORIZADO);
                break;

            case COMPRA:
                if (valorTaxaDeCambio > 0)
                    valorDaTransacao = valorDaTransacao * valorTaxaDeCambio;

                if (valorDaTransacao <= valorLimiteCartao)
                    transacao.setStatus(StatusTransacao.AUTORIZADO);
                else
                    transacao.setStatus(StatusTransacao.REJEITADO);
                break;

            case TRANSFERENCIA:
                if (valorTaxaDeCambio > 0)
                    valorDaTransacao = valorDaTransacao * valorTaxaDeCambio;

                if (valorDaTransacao <= valorDoSaldo)
                    transacao.setStatus(StatusTransacao.AUTORIZADO);
                else
                    transacao.setStatus(StatusTransacao.REJEITADO);
                break;

            default:
                transacao.setStatus(StatusTransacao.REJEITADO);
                break;
        }
        return transacao;
    }
}
