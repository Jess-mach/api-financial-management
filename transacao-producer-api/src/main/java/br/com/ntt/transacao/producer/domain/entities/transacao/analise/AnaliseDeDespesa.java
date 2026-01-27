package br.com.ntt.transacao.producer.domain.entities.transacao.analise;


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
