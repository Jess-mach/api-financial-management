package br.com.ntt.transacao.producer.application.usecases;

import br.com.ntt.transacao.producer.application.gateways.RepositorioDeTransacao;
import br.com.ntt.transacao.producer.domain.entities.transacao.AnaliseDeDespesa;
import br.com.ntt.transacao.producer.domain.entities.transacao.AnaliseDeDespesaItem;
import br.com.ntt.transacao.producer.domain.entities.transacao.AnaliseDeDespesaTotalizador;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class AnaliseDespesaTransacao {

    private final RepositorioDeTransacao repositorioDeTransacao;

    public AnaliseDespesaTransacao(RepositorioDeTransacao repositorioDeTransacao) {
        this.repositorioDeTransacao = repositorioDeTransacao;
    }

    public AnaliseDeDespesa visualizarGastos(UUID usuarioId) {

        // ---------------------------------  DIA ----------------------------------------

        List<AnaliseDeDespesaItem>  gastosPorDia = repositorioDeTransacao.visualizarGastosDia(usuarioId);

        BigDecimal valorTotalDia = gastosPorDia.stream()

//                           TODO - SOMAR E SUBTRAIR
                .map(AnaliseDeDespesaItem::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        AnaliseDeDespesaTotalizador dia = new AnaliseDeDespesaTotalizador(gastosPorDia, valorTotalDia);

        // ---------------------------------  MES ----------------------------------------

        List<AnaliseDeDespesaItem>  gastosPorMes = repositorioDeTransacao.visualizarGastosMes(usuarioId);

        BigDecimal valorTotalMes = gastosPorDia.stream()        // TODO - SOMAR E SUBTRAIR
                .map(AnaliseDeDespesaItem::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        AnaliseDeDespesaTotalizador mes = new AnaliseDeDespesaTotalizador(gastosPorMes, valorTotalMes);

        return new AnaliseDeDespesa(dia, mes);
    }
}

