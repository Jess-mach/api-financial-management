package br.com.ntt.transacao.producer.domain.entities.transacao;


public record AnaliseDeDespesa(
        AnaliseDeDespesaTotalizador dia,
        AnaliseDeDespesaTotalizador mes
) {
}
