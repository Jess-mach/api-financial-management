package br.com.ntt.transacao.producer.application.usecases;

import br.com.ntt.common.transacao.domain.entity.Transacao;
import br.com.ntt.transacao.producer.application.gateways.RepositorioDeTransacao;
import br.com.ntt.transacao.producer.domain.Usuario;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class BuscarTransacaoPorId {

    private final RepositorioDeTransacao repositorio;

    public BuscarTransacaoPorId(RepositorioDeTransacao repositorio) {
        this.repositorio = repositorio;
    }

    public Transacao buscarPorId(UUID id, Usuario usuarioLogado) {
        return this.repositorio.buscarPorId(id, usuarioLogado);
    }

}
