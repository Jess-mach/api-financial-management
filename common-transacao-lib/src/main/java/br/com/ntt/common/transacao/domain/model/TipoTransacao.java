package br.com.ntt.common.transacao.domain.model;

public enum TipoTransacao {

    DEPOSITO,
    SAQUE,
    COMPRA,
    TRANSFERENCIA,
    SAIDA_EM_DINHEIRO;

    public static String getDoCodigo(Integer tipo) {
        switch (tipo ) {
            case 0:
                return "DEPOSITO";
            case 1:
                return "SAQUE";
            case 2:
                return "COMPRA";
            case 3:
                return "TRANSFERENCIA";
            default:
                return null;
        }
    }
}
