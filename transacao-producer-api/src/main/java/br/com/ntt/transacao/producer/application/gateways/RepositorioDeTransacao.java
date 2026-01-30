package br.com.ntt.transacao.producer.application.gateways;



import br.com.ntt.common.transacao.domain.entity.RegistroDespesa;
import br.com.ntt.common.transacao.domain.entity.Transacao;
import br.com.ntt.transacao.producer.domain.Usuario;

import java.util.List;
import java.util.UUID;

public interface RepositorioDeTransacao {

    Transacao cadastrarTransacao(Transacao transacao, String token);
    List<Transacao> listarTodos(UUID usuarioId);
    Transacao buscarPorId (UUID id, Usuario usuarioLogado);
    List<RegistroDespesa>  visualizarGastosDia(UUID usuarioId);
    List<RegistroDespesa>  visualizarGastosMes(UUID usuarioId);

}
