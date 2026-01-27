package br.com.ntt.transacao.producer.domain.model;

public enum TipoTransacao {

    DEPOSITO,
    SAQUE,
    COMPRA,
    TRANSFERENCIA;

    public static String getDoCodigo(String tipo) {
        switch (tipo ) {
            case "0":
                return "DEPOSITO";
            case "1":
                return "SAQUE";
            case "2":
                return "COMPRA";
            case "3":
                return "TRANSFERENCIA";
            default:
                return null;
        }
    }
}
