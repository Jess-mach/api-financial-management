package br.com.ntt.transacao.consumer.application.service;

import br.com.ntt.transacao.consumer.domain.entities.conta.SaldoConta;
import br.com.ntt.transacao.consumer.domain.entities.moeda.ConversorMoeda;
import br.com.ntt.transacao.consumer.domain.entities.transacao.Transacao;
import br.com.ntt.transacao.consumer.domain.model.StatusTransacao;

public class ValidadorDeTransacao {

    private static Transacao validarTransacao(Transacao transacao, SaldoConta saldoConta, ConversorMoeda conversorMoeda) {
        Double valorDoSaldo = saldoConta.getSaldo().doubleValue();
        Double valorDaTransacao = transacao.getValor().doubleValue();
        Double valorTaxaDeCambio = conversorMoeda.getCotacoes().get(0).getCotacaoVenda().doubleValue();

        if(!transacao.getMoeda().equals("BRL")){
            transacao.atualizaTaxaDeCambio(valorTaxaDeCambio);
        }

        switch (transacao.getTipo()) {
            case SAQUE:
                if(valorTaxaDeCambio > 0) {
                    valorDaTransacao = valorDaTransacao * valorTaxaDeCambio;
                }
                if(valorDaTransacao <= valorDoSaldo)
                    transacao.atualizaStatus(StatusTransacao.REJEITADO);
                transacao.atualizaStatus(StatusTransacao.AUTORIZADO);
                break;

            case DEPOSITO:
                transacao.atualizaStatus(StatusTransacao.AUTORIZADO);
                break;

            case COMPRA:
                if(valorTaxaDeCambio > 0)
                    valorDaTransacao = valorDaTransacao * valorTaxaDeCambio;
                if(valorDaTransacao <= valorDoSaldo)
                    transacao.atualizaStatus(StatusTransacao.REJEITADO);
                transacao.atualizaStatus(StatusTransacao.AUTORIZADO);
                break;

            case TRANSFERENCIA:
                if(valorTaxaDeCambio > 0)
                    valorDaTransacao = valorDaTransacao * valorTaxaDeCambio;
                if(valorDaTransacao <= valorDoSaldo)
                    transacao.atualizaStatus(StatusTransacao.REJEITADO);
                break;

            default:
                transacao.atualizaStatus(StatusTransacao.REJEITADO);
                break;
        }
        return transacao;
    }


}
