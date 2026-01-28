package br.com.ntt.transacao.consumer.domain.entity.conta;

import java.math.BigDecimal;

public class SaldoConta {

        private String nome;
        private String conta;
        private BigDecimal saldo;
        private String id;
        public BigDecimal limiteCartao;

    public SaldoConta(String nome, String conta, BigDecimal saldo, String id, BigDecimal limiteCartao) {
        this.nome = nome;
        this.conta = conta;
        this.saldo = saldo;
        this.id = id;
        this.limiteCartao = limiteCartao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getLimiteCartao() {
        return limiteCartao;
    }

    public void setLimiteCartao(BigDecimal limiteCartao) {
        this.limiteCartao = limiteCartao;
    }
}

