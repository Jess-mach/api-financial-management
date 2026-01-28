package br.com.ntt.transacao.consumer.application.gateways;

import br.com.ntt.transacao.consumer.domain.entity.conta.SaldoConta;
import br.com.ntt.transacao.consumer.domain.entity.transacao.Transacao;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface RepositorioSaldoCliente {

    SaldoConta buscarPorId(Long id);

    void atualizarSaldo(SaldoConta saldoConta, Transacao valor) throws JsonProcessingException;
}
