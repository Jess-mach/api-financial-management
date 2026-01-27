package br.com.ntt.transacao.producer.infra.persistence;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface AnaliseDeDespesaCampos {
    LocalDate getDataHoraSolicitacao();
    String getTipo();
    BigDecimal getValor();
    Long getQuantidade();
}
