package br.com.ntt.transacao.consumer.domain.entities.cotacao;

import br.com.ntt.transacao.consumer.domain.model.TipoBoletim;

import java.math.BigDecimal;

public class Cotacao {

    private BigDecimal paridadeCompra;
    private BigDecimal paridadeVenda;
    private BigDecimal cotacaoCompra;
    private BigDecimal cotacaoVenda;
    private String dataHoraCotacao;
    private TipoBoletim tipoBoletim;

    public Cotacao(BigDecimal paridadeCompra, BigDecimal paridadeVenda, BigDecimal cotacaoCompra, BigDecimal cotacaoVenda, String dataHoraCotacao, TipoBoletim tipoBoletim) {
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

    public TipoBoletim getTipoBoletim() {
        return tipoBoletim;
    }
}
