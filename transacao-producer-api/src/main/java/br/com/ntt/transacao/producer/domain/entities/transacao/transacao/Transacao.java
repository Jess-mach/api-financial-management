package br.com.ntt.transacao.producer.domain.entities.transacao.transacao;

import br.com.ntt.transacao.producer.domain.model.StatusTransacao;
import br.com.ntt.transacao.producer.domain.model.TipoTransacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Transacao {

    private UUID id;
    private UUID usuarioId;
    private BigDecimal valor;
    private TipoTransacao tipo;
    private StatusTransacao status;
    private LocalDateTime dataHoraSolicitacao;
    private LocalDateTime dataHoraFinalizacao;
    private String moeda;
    private BigDecimal taxaCambio;
    private String descricao;


    public Transacao(UUID id, UUID usuarioId, BigDecimal valor, TipoTransacao tipo, StatusTransacao status, LocalDateTime dataHoraSolicitacao, LocalDateTime dataHoraFinalizacao, String moeda, BigDecimal taxaCambio, String descricao) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.valor = valor;
        this.tipo = tipo;
        this.status = status;
        this.dataHoraSolicitacao = dataHoraSolicitacao;
        this.dataHoraFinalizacao = dataHoraFinalizacao;
        this.moeda = moeda;
        this.taxaCambio = taxaCambio;
        this.descricao = descricao;
    }


    public UUID getId() {
        return id;
    }

    public UUID getUsuarioId() {
        return usuarioId;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public TipoTransacao getTipo() {
        return tipo;
    }

    public StatusTransacao getStatus() {
        return status;
    }

    public LocalDateTime getDataHoraSolicitacao() {
        return dataHoraSolicitacao;
    }

    public LocalDateTime getDataHoraFinalizacao() {
        return dataHoraFinalizacao;
    }

    public String getMoeda() {
        return moeda;
    }

    public BigDecimal getTaxaCambio() {
        return taxaCambio;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setUsuarioId(UUID usuarioId) {
        this.usuarioId = usuarioId;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public void setTipo(TipoTransacao tipo) {
        this.tipo = tipo;
    }

    public void setStatus(StatusTransacao status) {
        this.status = status;
    }

    public void setDataHoraSolicitacao(LocalDateTime dataHoraSolicitacao) {
        this.dataHoraSolicitacao = dataHoraSolicitacao;
    }

    public void setDataHoraFinalizacao(LocalDateTime dataHoraFinalizacao) {
        this.dataHoraFinalizacao = dataHoraFinalizacao;
    }

    public void setMoeda(String moeda) {
        this.moeda = moeda;
    }

    public void setTaxaCambio(BigDecimal taxaCambio) {
        this.taxaCambio = taxaCambio;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}