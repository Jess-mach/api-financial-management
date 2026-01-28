package br.com.ntt.common.transacao.domain.entity;

import java.math.BigDecimal;
import java.util.List;

public class TotalizadorDespesa {

    private List<RegistroDespesa> despesas;
    private BigDecimal valorTotal;

    public TotalizadorDespesa(List<RegistroDespesa> despesas, BigDecimal valorTotal) {
        this.despesas = despesas;
        this.valorTotal = valorTotal;
    }

    public List<RegistroDespesa> getDespesas() {
        return despesas;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }
}
