package br.com.ntt.common.transacao.infra.persistence;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface AnaliseDeDespesaCampos {
    LocalDate getDataHoraSolicitacao();
    String getTipo();
    BigDecimal getValor();
    Long getQuantidade();
}
