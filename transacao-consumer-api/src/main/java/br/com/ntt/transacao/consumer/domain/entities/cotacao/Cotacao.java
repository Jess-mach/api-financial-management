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

    public void setParidadeCompra(BigDecimal paridadeCompra) {
        this.paridadeCompra = paridadeCompra;
    }

    public BigDecimal getParidadeVenda() {
        return paridadeVenda;
    }

    public void setParidadeVenda(BigDecimal paridadeVenda) {
        this.paridadeVenda = paridadeVenda;
    }

    public BigDecimal getCotacaoCompra() {
        return cotacaoCompra;
    }

    public void setCotacaoCompra(BigDecimal cotacaoCompra) {
        this.cotacaoCompra = cotacaoCompra;
    }

    public BigDecimal getCotacaoVenda() {
        return cotacaoVenda;
    }

    public void setCotacaoVenda(BigDecimal cotacaoVenda) {
        this.cotacaoVenda = cotacaoVenda;
    }

    public String getDataHoraCotacao() {
        return dataHoraCotacao;
    }

    public void setDataHoraCotacao(String dataHoraCotacao) {
        this.dataHoraCotacao = dataHoraCotacao;
    }

    public String getTipoBoletim() {
        return tipoBoletim;
    }

    public void setTipoBoletim(String tipoBoletim) {
        this.tipoBoletim = tipoBoletim;
    }
}
