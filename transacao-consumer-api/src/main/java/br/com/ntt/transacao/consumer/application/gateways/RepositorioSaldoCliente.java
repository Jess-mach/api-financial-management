package br.com.ntt.transacao.consumer.application.gateways;

import br.com.ntt.transacao.consumer.domain.entities.SaldoConta;
import br.com.ntt.transacao.consumer.domain.entities.transacao.Transacao;

public interface RepositorioSaldoCliente {

    SaldoConta buscarPorId(Long id);

}
