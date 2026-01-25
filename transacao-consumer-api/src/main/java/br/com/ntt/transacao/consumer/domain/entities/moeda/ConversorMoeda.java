package br.com.ntt.transacao.consumer.domain.entities.moeda;

public class ConversorMoeda {

    private String moeda;
    private String data;

    public ConversorMoeda(String moeda, String data) {
        this.moeda = moeda;
        this.data = data;
    }

    public String getMoeda() {
        return moeda;
    }

    public String getData() {
        return data;
    }
}
