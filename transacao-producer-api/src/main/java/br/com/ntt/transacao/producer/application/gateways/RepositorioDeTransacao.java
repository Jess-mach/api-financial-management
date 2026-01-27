package br.com.ntt.transacao.producer.application.gateways;

import br.com.ntt.transacao.producer.domain.entities.transacao.AnaliseDeDespesa;
import br.com.ntt.transacao.producer.domain.entities.transacao.AnaliseDeDespesaItem;
import br.com.ntt.transacao.producer.domain.entities.transacao.Transacao;

import java.util.List;
import java.util.UUID;

public interface RepositorioDeTransacao {

    Transacao cadastrarTransacao(Transacao transacao);
    List<Transacao> listarTodos();
    Transacao buscarPorId (UUID id);
    List<AnaliseDeDespesaItem>  visualizarGastosDia(UUID usuarioId);
    List<AnaliseDeDespesaItem>  visualizarGastosMes(UUID usuarioId);

}
