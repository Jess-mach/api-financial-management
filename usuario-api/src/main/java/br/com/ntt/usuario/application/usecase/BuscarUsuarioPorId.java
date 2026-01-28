package br.com.ntt.usuario.application.usecase;

import br.com.ntt.usuario.application.gateways.UsuarioRepository;
import br.com.ntt.usuario.domain.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class BuscarUsuarioPorId {

    private final UsuarioRepository repositorio;

    public BuscarUsuarioPorId(UsuarioRepository repositorio) {
        this.repositorio = repositorio;
    }

    public Usuario buscarPorId(UUID id){
        return this.repositorio.findById(id);
    }

}
