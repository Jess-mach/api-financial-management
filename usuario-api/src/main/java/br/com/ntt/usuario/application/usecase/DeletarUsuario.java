package br.com.ntt.usuario.application.usecase;

import br.com.ntt.usuario.application.gateways.RepositorioDeUsuario;
import br.com.ntt.usuario.domain.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DeletarUsuario {

    private final RepositorioDeUsuario repositorioDeUsuario;

    private final ValidadarUsuarioLogado validadarUsuarioLogado;

    public DeletarUsuario(RepositorioDeUsuario repositorioDeUsuario, ValidadarUsuarioLogado validadarUsuarioLogado) {
        this.repositorioDeUsuario = repositorioDeUsuario;
        this.validadarUsuarioLogado = validadarUsuarioLogado;
    }

    public void executar(UUID id, Usuario usuarioLogado) {
        validadarUsuarioLogado.validaUsuarioGerenteOuAdministrador(usuarioLogado);

        repositorioDeUsuario.deleteById(id);
    }
}
