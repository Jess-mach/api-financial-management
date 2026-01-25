package br.com.ntt.transacao.consumer.application.gateways;

import br.com.ntt.transacao.consumer.domain.entities.conta.SaldoConta;

public interface RepositorioSaldoCliente {

    SaldoConta buscarPorId(Long id);

}
