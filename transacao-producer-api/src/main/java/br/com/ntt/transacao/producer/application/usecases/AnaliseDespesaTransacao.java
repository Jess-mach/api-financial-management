package br.com.ntt.transacao.producer.application.usecases;

import br.com.ntt.common.transacao.domain.entity.AnaliseDeDespesa;
import br.com.ntt.common.transacao.domain.entity.RegistroDespesa;
import br.com.ntt.common.transacao.domain.entity.TotalizadorDespesa;
import br.com.ntt.transacao.producer.application.gateways.RepositorioDeTransacao;

import br.com.ntt.transacao.producer.domain.Usuario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class AnaliseDespesaTransacao {

    private final RepositorioDeTransacao repositorioDeTransacao;

    public AnaliseDespesaTransacao(RepositorioDeTransacao repositorioDeTransacao) {
        this.repositorioDeTransacao = repositorioDeTransacao;
    }

    public AnaliseDeDespesa visualizarGastos(UUID usuarioId, Usuario usuarioLogado) {

        if (!usuarioId.equals(usuarioLogado.id()) && !usuarioLogado.perfilUsuario().equals("GERENTE"))
            throw new AccessDeniedException("Acesso negado");

        TotalizadorDespesa dia = calcularGastosPorDia(usuarioId);

        TotalizadorDespesa mes = calcularGastosPorMes(usuarioId);

        return new AnaliseDeDespesa(dia, mes);
    }

    private TotalizadorDespesa calcularGastosPorMes(UUID usuarioId) {
        log.info("calculando despesas por mes");

        List<RegistroDespesa> gastosPorMes = repositorioDeTransacao.visualizarGastosMes(usuarioId);
        BigDecimal valorTotalMes = contabilizarValorTotal(gastosPorMes);
        TotalizadorDespesa mes = new TotalizadorDespesa(gastosPorMes, valorTotalMes);

        log.info("despesas por mes calculadas");

        return mes;
    }

    private TotalizadorDespesa calcularGastosPorDia(UUID usuarioId) {
        log.info("calculando despesas por dia");

        List<RegistroDespesa> gastosPorDia = repositorioDeTransacao.visualizarGastosDia(usuarioId);
        BigDecimal valorTotalDia = contabilizarValorTotal(gastosPorDia);
        TotalizadorDespesa dia = new TotalizadorDespesa(gastosPorDia, valorTotalDia);

        log.info("despesas por dia calculadas");

        return dia;
    }

    private static BigDecimal contabilizarValorTotal(List<RegistroDespesa> gastos) {
        BigDecimal valorTotal = BigDecimal.ZERO;

        log.info("totalizando valores das despesas");

        for (RegistroDespesa despesa : gastos) {
            if (despesa.getTipo().equals("DEPOSITO")) {
                valorTotal = valorTotal.add(despesa.getValor());
            } else
                valorTotal = valorTotal.subtract(despesa.getValor());
        }

        log.info("valores totais calculados");

        return valorTotal;
    }

}

