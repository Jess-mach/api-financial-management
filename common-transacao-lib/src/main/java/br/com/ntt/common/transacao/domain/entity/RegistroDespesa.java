package br.com.ntt.common.transacao.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RegistroDespesa {

    private LocalDate data;
    private String tipo;
    private BigDecimal valor;
    private Long quantidade;

    public RegistroDespesa(LocalDate data, String tipo, BigDecimal valor, Long quantidade) {
        this.data = data;
        this.tipo = tipo;
        this.valor = valor;
        this.quantidade = quantidade;
    }

    public LocalDate getData() {
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
