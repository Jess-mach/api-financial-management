package br.com.ntt.usuario.application.usecase;

import br.com.ntt.usuario.domain.entity.Usuario;
import br.com.ntt.usuario.application.gateways.RepositorioDeUsuario;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListarUsuario {

    private final RepositorioDeUsuario repositorioDeUsuario;

    private final ValidadarUsuarioLogado validadarUsuarioLogado;

    public ListarUsuario(RepositorioDeUsuario repositorioDeUsuario, ValidadarUsuarioLogado validadarUsuarioLogado) {
        this.repositorioDeUsuario = repositorioDeUsuario;
        this.validadarUsuarioLogado = validadarUsuarioLogado;
    }

    public List<Usuario> executar(Usuario usuarioLogado){
        validadarUsuarioLogado.validaUsuarioGerenteOuAdministrador(usuarioLogado);

        return repositorioDeUsuario.findAll();
    }
}
