package br.com.ntt.transacao.consumer.domain.entities.conta;

import java.math.BigDecimal;

public class SaldoConta {

        private String name;
        private String conta;
        private BigDecimal saldo;
        private String routingNumber;
        private String id;

    public SaldoConta(String name, String conta, BigDecimal saldo, String routingNumber, String id) {
        this.name = name;
        this.conta = conta;
        this.saldo = saldo;
        this.routingNumber = routingNumber;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getRoutingNumber() {
        return routingNumber;
    }

    public void setRoutingNumber(String routingNumber) {
        this.routingNumber = routingNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

