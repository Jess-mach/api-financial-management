package br.com.ntt.common.transacao.domain.entity;


public class AnaliseDeDespesa {
        private TotalizadorDespesa dia;
        private TotalizadorDespesa mes;

    public AnaliseDeDespesa(TotalizadorDespesa dia, TotalizadorDespesa mes) {
        this.dia = dia;
        this.mes = mes;
    }

    public TotalizadorDespesa getDia() {
        return dia;
    }

    public TotalizadorDespesa getMes() {
        return mes;
    }
}
