package br.com.ntt.transacao.producer.application.gateways;



import br.com.ntt.common.transacao.domain.entity.RegistroDespesa;
import br.com.ntt.common.transacao.domain.entity.Transacao;

import java.util.List;
import java.util.UUID;

public interface RepositorioDeTransacao {

    Transacao cadastrarTransacao(Transacao transacao);
    List<Transacao> listarTodos();
    Transacao buscarPorId (UUID id);
    List<RegistroDespesa>  visualizarGastosDia(UUID usuarioId);
    List<RegistroDespesa>  visualizarGastosMes(UUID usuarioId);

}
