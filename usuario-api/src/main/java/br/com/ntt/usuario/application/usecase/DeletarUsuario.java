package br.com.ntt.usuario.application.usecase;

import br.com.ntt.usuario.application.gateways.RepositorioDeUsuario;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DeletarUsuario {

    private final RepositorioDeUsuario repositorioDeUsuario;

    public DeletarUsuario(RepositorioDeUsuario repositorioDeUsuario) {
        this.repositorioDeUsuario = repositorioDeUsuario;
    }

    public void executar(UUID id) {
        repositorioDeUsuario.deleteById(id);
    }
}
