package br.com.ntt.common.transacao.infra.persistence;

import br.com.ntt.common.transacao.domain.model.StatusTransacao;
import br.com.ntt.common.transacao.domain.model.TipoTransacao;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transacoes")
public class TransacaoEntity {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @NotNull
    private UUID usuarioId;

    @NotNull
    private BigDecimal valor;

    @NotNull
    private TipoTransacao tipo;

    @NotNull
    private StatusTransacao status;

    @NotNull
    private LocalDateTime dataHoraSolicitacao;
    private LocalDateTime dataHoraFinalizacao;

    @NotNull
    private String moeda;

    private BigDecimal taxaCambio;

    @NotNull
    private String descricao;

    @NotNull
    private Long conta;

    public TransacaoEntity(UUID id, UUID usuarioId, BigDecimal valor, TipoTransacao tipo, StatusTransacao status, LocalDateTime dataHoraSolicitacao, LocalDateTime dataHoraFinalizacao, String moeda, BigDecimal taxaCambio, String descricao, Long conta) {
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
        this.conta = conta;
    }

    public Long getConta() {
        return conta;
    }

    public void setConta(Long conta) {
        this.conta = conta;
    }

    public TransacaoEntity() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(UUID usuarioId) {
        this.usuarioId = usuarioId;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public TipoTransacao getTipo() {
        return tipo;
    }

    public void setTipo(TipoTransacao tipo) {
        this.tipo = tipo;
    }

    public StatusTransacao getStatus() {
        return status;
    }

    public void setStatus(StatusTransacao status) {
        this.status = status;
    }

    public LocalDateTime getDataHoraSolicitacao() {
        return dataHoraSolicitacao;
    }

    public void setDataHoraSolicitacao(LocalDateTime dataHoraSolicitacao) {
        this.dataHoraSolicitacao = dataHoraSolicitacao;
    }

    public LocalDateTime getDataHoraFinalizacao() {
        return dataHoraFinalizacao;
    }

    public void setDataHoraFinalizacao(LocalDateTime dataHoraFinalizacao) {
        this.dataHoraFinalizacao = dataHoraFinalizacao;
    }

    public String getMoeda() {
        return moeda;
    }

    public void setMoeda(String moeda) {
        this.moeda = moeda;
    }

    public BigDecimal getTaxaCambio() {
        return taxaCambio;
    }

    public void setTaxaCambio(BigDecimal taxaCambio) {
        this.taxaCambio = taxaCambio;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}
