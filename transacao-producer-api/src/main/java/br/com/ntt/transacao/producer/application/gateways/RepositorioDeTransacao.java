package br.com.ntt.transacao.producer.application.gateways;

import br.com.ntt.transacao.producer.domain.entities.transacao.analise.AnaliseDeDespesaItem;
import br.com.ntt.transacao.producer.domain.entities.transacao.transacao.Transacao;

import java.util.List;
import java.util.UUID;

public interface RepositorioDeTransacao {

    Transacao cadastrarTransacao(Transacao transacao);
    List<Transacao> listarTodos();
    Transacao buscarPorId (UUID id);
    List<AnaliseDeDespesaItem>  visualizarGastosDia(UUID usuarioId);
    List<AnaliseDeDespesaItem>  visualizarGastosMes(UUID usuarioId);

}
