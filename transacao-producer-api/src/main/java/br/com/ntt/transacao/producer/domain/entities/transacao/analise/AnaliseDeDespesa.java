package br.com.ntt.transacao.producer.domain.entities.transacao.analise;


public record AnaliseDeDespesa(
        AnaliseDeDespesaTotalizador dia,
        AnaliseDeDespesaTotalizador mes
) {
}
