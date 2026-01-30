package br.com.ntt.common.transacao.domain.entity;

import java.math.BigDecimal;

public class RegistroDespesa {

    private String data;
    private String tipo;
    private BigDecimal valor;
    private Long quantidade;

    public RegistroDespesa(String data, String tipo, BigDecimal valor, Long quantidade) {
        this.data = data;
        this.tipo = tipo;
        this.valor = valor;
        this.quantidade = quantidade;
    }

    public String getData() {
        return data;
    }

    public String getTipo() {
        return tipo;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Long getQuantidade() {
        return quantidade;
    }
}
