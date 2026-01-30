package br.com.ntt.usuario.application.usecase;

import br.com.ntt.usuario.application.gateways.RepositorioDeUsuario;
import br.com.ntt.usuario.domain.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class BuscarUsuarioPorId {

    private final RepositorioDeUsuario repositorio;

    public BuscarUsuarioPorId(RepositorioDeUsuario repositorio) {
        this.repositorio = repositorio;
    }

    public Usuario buscarPorId(UUID id){
        return this.repositorio.findById(id);
    }

}
