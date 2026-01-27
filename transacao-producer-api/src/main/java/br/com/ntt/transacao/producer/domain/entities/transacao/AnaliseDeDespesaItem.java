package br.com.ntt.transacao.producer.domain.entities.transacao;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AnaliseDeDespesaItem {

    private LocalDate data;
    private String tipo;
    private BigDecimal valor;
    private Long quantidade;

    public AnaliseDeDespesaItem(LocalDate data, String tipo, BigDecimal valor, Long quantidade) {
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
