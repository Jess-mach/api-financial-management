package br.com.ntt.transacao.producer.application.usecases;

import br.com.ntt.transacao.producer.application.gateways.RepositorioDeTransacao;
import br.com.ntt.transacao.producer.domain.entities.transacao.analise.AnaliseDeDespesa;
import br.com.ntt.transacao.producer.domain.entities.transacao.analise.AnaliseDeDespesaItem;
import br.com.ntt.transacao.producer.domain.entities.transacao.analise.AnaliseDeDespesaTotalizador;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class AnaliseDespesaTransacao {

    private final RepositorioDeTransacao repositorioDeTransacao;

    public AnaliseDespesaTransacao(RepositorioDeTransacao repositorioDeTransacao) {
        this.repositorioDeTransacao = repositorioDeTransacao;
    }

    public AnaliseDeDespesa visualizarGastos(UUID usuarioId) {

        AnaliseDeDespesaTotalizador dia = calcularGastosPorDia(usuarioId);

        AnaliseDeDespesaTotalizador mes = calcularGastosPorMes(usuarioId);

        return new AnaliseDeDespesa(dia, mes);
    }

    private AnaliseDeDespesaTotalizador calcularGastosPorMes(UUID usuarioId) {
        List<AnaliseDeDespesaItem>  gastosPorMes = repositorioDeTransacao.visualizarGastosMes(usuarioId);
        BigDecimal valorTotalMes = contabilizarValorTotal(gastosPorMes);
        AnaliseDeDespesaTotalizador mes = new AnaliseDeDespesaTotalizador(gastosPorMes, valorTotalMes);
        return mes;
    }

    private AnaliseDeDespesaTotalizador calcularGastosPorDia(UUID usuarioId) {
        List<AnaliseDeDespesaItem>  gastosPorDia = repositorioDeTransacao.visualizarGastosDia(usuarioId);
        BigDecimal valorTotalDia = contabilizarValorTotal(gastosPorDia);
        AnaliseDeDespesaTotalizador dia = new AnaliseDeDespesaTotalizador(gastosPorDia, valorTotalDia);
        return dia;
    }

    private static BigDecimal contabilizarValorTotal(List<AnaliseDeDespesaItem> gastos) {
        BigDecimal valorTotal = BigDecimal.ZERO;

        for (AnaliseDeDespesaItem despesa : gastos) {
            if (despesa.getTipo().equals("DEPOSITO")) {
                valorTotal = valorTotal.add(despesa.getValor());
            }else
                valorTotal = valorTotal.subtract(despesa.getValor());
        }
        return valorTotal;
    }

}

