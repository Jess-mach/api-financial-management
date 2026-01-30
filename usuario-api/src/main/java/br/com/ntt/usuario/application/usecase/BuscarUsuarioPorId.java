package br.com.ntt.usuario.application.usecase;

import br.com.ntt.usuario.application.gateways.RepositorioDeUsuario;
import br.com.ntt.usuario.domain.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class BuscarUsuarioPorId {

    private final RepositorioDeUsuario repositorio;

    private final ValidadarUsuarioLogado validadarUsuarioLogado;

    public BuscarUsuarioPorId(RepositorioDeUsuario repositorio, ValidadarUsuarioLogado validadarUsuarioLogado) {
        this.repositorio = repositorio;
        this.validadarUsuarioLogado = validadarUsuarioLogado;
    }

    public Usuario buscarPorId(UUID id, Usuario usuarioLogado){
        validadarUsuarioLogado.validaSeEhProprioUsuarioLogadoOuUsuarioGerenteOuAdministrador(id, usuarioLogado);

        return this.repositorio.findById(id);
    }

}
