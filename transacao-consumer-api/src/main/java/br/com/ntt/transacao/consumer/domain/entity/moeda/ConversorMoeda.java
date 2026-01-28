package br.com.ntt.transacao.consumer.domain.entity.moeda;

import br.com.ntt.transacao.consumer.domain.entity.cotacao.Cotacao;

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

    public void setMoeda(String moeda) {
        this.moeda = moeda;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<Cotacao> getCotacoes() {
        return cotacoes;
    }

    public void setCotacoes(List<Cotacao> cotacoes) {
        this.cotacoes = cotacoes;
    }
}
