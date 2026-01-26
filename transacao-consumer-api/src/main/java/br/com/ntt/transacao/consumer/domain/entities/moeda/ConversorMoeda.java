package br.com.ntt.transacao.consumer.domain.entities.moeda;

import br.com.ntt.transacao.consumer.domain.entities.cotacao.Cotacao;

import java.util.List;

public class ConversorMoeda {

    private String moeda;
    private String data;
    List<Cotacao> cotacoes;

    public ConversorMoeda(String moeda, String data, List<Cotacao> cotacoes) {
        this.moeda = moeda;
        this.data = data;
        this.cotacoes = cotacoes;
    }

    public String getMoeda() {
        return moeda;
    }

    public String getData() {
        return data;
    }

    public List<Cotacao> getCotacoes() {
        return cotacoes;
    }
}
