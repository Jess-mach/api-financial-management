package br.com.ntt.usuario.application.usecase;

import br.com.ntt.usuario.application.gateways.RepositorioDeUsuario;
import br.com.ntt.usuario.application.gateways.RepositorioDeEncriptacao;
import br.com.ntt.usuario.domain.entity.Usuario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AtualizarUsuario {

    private final RepositorioDeUsuario repositorioDeUsuario;

    private final RepositorioDeEncriptacao repositorioDeEncriptacao;

    private final ValidadarUsuarioLogado validadarUsuarioLogado;

    public AtualizarUsuario(RepositorioDeUsuario repositorioDeUsuario, RepositorioDeEncriptacao repositorioDeEncriptacao, ValidadarUsuarioLogado validadarUsuarioLogado) {
        this.repositorioDeUsuario = repositorioDeUsuario;
        this.repositorioDeEncriptacao = repositorioDeEncriptacao;
        this.validadarUsuarioLogado = validadarUsuarioLogado;
    }

    public Usuario executar(Usuario usuarioAtualizacao, Usuario usuarioLogado) {

        Usuario usuarioEntidade = repositorioDeUsuario.findById(usuarioAtualizacao.getId());

        String senhaHash = repositorioDeEncriptacao.encode(usuarioAtualizacao.getSenha());

        usuarioAtualizacao.setId(usuarioEntidade.getId());
        usuarioAtualizacao.setLogin(usuarioEntidade.getLogin());

        return repositorioDeUsuario.save(usuarioAtualizacao, senhaHash);
    }
}
