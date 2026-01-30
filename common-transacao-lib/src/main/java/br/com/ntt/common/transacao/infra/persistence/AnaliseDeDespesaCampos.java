package br.com.ntt.common.transacao.infra.persistence;

public interface AnaliseDeDespesaCampos {
    String getDataHoraSolicitacao();

    Integer getTipo();

    Double getValor();

    Integer getQuantidade();
}
