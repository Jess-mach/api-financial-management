package br.com.ntt.transacao.producer.domain.entities.transacao;

import java.math.BigDecimal;
import java.util.List;

public class AnaliseDeDespesaTotalizador {

    private List<AnaliseDeDespesaItem> despesas;
    private BigDecimal valorTotal;

    public AnaliseDeDespesaTotalizador(List<AnaliseDeDespesaItem> despesas, BigDecimal valorTotal) {
        this.despesas = despesas;
        this.valorTotal = valorTotal;
    }

    public List<AnaliseDeDespesaItem> getDespesas() {
        return despesas;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }
}
