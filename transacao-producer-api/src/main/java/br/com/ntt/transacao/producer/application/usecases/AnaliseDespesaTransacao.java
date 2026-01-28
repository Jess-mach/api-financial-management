package br.com.ntt.transacao.producer.application.usecases;

import br.com.ntt.transacao.producer.application.gateways.RepositorioDeTransacao;
import br.com.ntt.transacao.producer.domain.entity.AnaliseDeDespesa;
import br.com.ntt.transacao.producer.domain.entity.RegistroDespesa;
import br.com.ntt.transacao.producer.domain.entity.TotalizadorDespesa;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Component
public class AnaliseDespesaTransacao {

    private final RepositorioDeTransacao repositorioDeTransacao;

    public AnaliseDespesaTransacao(RepositorioDeTransacao repositorioDeTransacao) {
        this.repositorioDeTransacao = repositorioDeTransacao;
    }

    public AnaliseDeDespesa visualizarGastos(UUID usuarioId) {

        TotalizadorDespesa dia = calcularGastosPorDia(usuarioId);

        TotalizadorDespesa mes = calcularGastosPorMes(usuarioId);

        return new AnaliseDeDespesa(dia, mes);
    }

    private TotalizadorDespesa calcularGastosPorMes(UUID usuarioId) {
        List<RegistroDespesa>  gastosPorMes = repositorioDeTransacao.visualizarGastosMes(usuarioId);
        BigDecimal valorTotalMes = contabilizarValorTotal(gastosPorMes);
        TotalizadorDespesa mes = new TotalizadorDespesa(gastosPorMes, valorTotalMes);
        return mes;
    }

    private TotalizadorDespesa calcularGastosPorDia(UUID usuarioId) {
        List<RegistroDespesa>  gastosPorDia = repositorioDeTransacao.visualizarGastosDia(usuarioId);
        BigDecimal valorTotalDia = contabilizarValorTotal(gastosPorDia);
        TotalizadorDespesa dia = new TotalizadorDespesa(gastosPorDia, valorTotalDia);
        return dia;
    }

    private static BigDecimal contabilizarValorTotal(List<RegistroDespesa> gastos) {
        BigDecimal valorTotal = BigDecimal.ZERO;

        for (RegistroDespesa despesa : gastos) {
            if (despesa.getTipo().equals("DEPOSITO")) {
                valorTotal = valorTotal.add(despesa.getValor());
            }else
                valorTotal = valorTotal.subtract(despesa.getValor());
        }
        return valorTotal;
    }

}

