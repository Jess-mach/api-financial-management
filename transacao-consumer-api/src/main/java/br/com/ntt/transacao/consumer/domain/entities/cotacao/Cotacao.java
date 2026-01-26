package br.com.ntt.transacao.consumer.domain.entities.cotacao;

import java.math.BigDecimal;

public class Cotacao {

    private BigDecimal paridadeCompra;
    private BigDecimal paridadeVenda;
    private BigDecimal cotacaoCompra;
    private BigDecimal cotacaoVenda;
    private String dataHoraCotacao;
    private String tipoBoletim;

    public Cotacao(BigDecimal paridadeCompra, BigDecimal paridadeVenda, BigDecimal cotacaoCompra, BigDecimal cotacaoVenda, String dataHoraCotacao, String tipoBoletim) {
        this.paridadeCompra = paridadeCompra;
        this.paridadeVenda = paridadeVenda;
        this.cotacaoCompra = cotacaoCompra;
        this.cotacaoVenda = cotacaoVenda;
        this.dataHoraCotacao = dataHoraCotacao;
        this.tipoBoletim = tipoBoletim;
    }

    public BigDecimal getParidadeCompra() {
        return paridadeCompra;
    }

    public BigDecimal getParidadeVenda() {
        return paridadeVenda;
    }

    public BigDecimal getCotacaoCompra() {
        return cotacaoCompra;
    }

    public BigDecimal getCotacaoVenda() {
        return cotacaoVenda;
    }

    public String getDataHoraCotacao() {
        return dataHoraCotacao;
    }

    public String getTipoBoletim() {
        return tipoBoletim;
    }
}
