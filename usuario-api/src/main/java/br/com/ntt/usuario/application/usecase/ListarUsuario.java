package br.com.ntt.usuario.application.usecase;

import br.com.ntt.usuario.domain.entity.Usuario;
import br.com.ntt.usuario.application.gateways.RepositorioDeUsuario;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListarUsuario {

    private final RepositorioDeUsuario repositorioDeUsuario;

    public ListarUsuario(RepositorioDeUsuario repositorioDeUsuario) {
        this.repositorioDeUsuario = repositorioDeUsuario;
    }

    public List<Usuario> executar(){
        return repositorioDeUsuario.findAll();
    }
}
