package br.com.ntt.transacao.consumer.application.usecases;

import br.com.ntt.transacao.consumer.application.gateways.RepositorioDeTransacao;
import br.com.ntt.transacao.consumer.domain.entities.transacao.Transacao;

import java.util.UUID;

public class BuscarTransacaoPorId {

    private final RepositorioDeTransacao repositorio;

    public BuscarTransacaoPorId(RepositorioDeTransacao repositorio) {
        this.repositorio = repositorio;
    }

    public Transacao buscarPorId(UUID id){
        return this.repositorio.busacarPorId(id);
    }

}
