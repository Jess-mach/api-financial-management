package br.com.ntt.usuario.application.usecase;

import br.com.ntt.usuario.application.gateways.UsuarioRepository;
import br.com.ntt.usuario.config.PasswordService;
import br.com.ntt.usuario.domain.entity.Usuario;
import org.springframework.stereotype.Component;

@Component
public class AtualizarUsuario {

    private final UsuarioRepository usuarioRepository;

    private final PasswordService passwordService;

    public AtualizarUsuario(UsuarioRepository usuarioRepository, PasswordService passwordService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordService = passwordService;
    }

    public Usuario executar(Usuario usuarioAtualizacao) {
        Usuario usuarioEntidade = usuarioRepository.findById(usuarioAtualizacao.getId());

        String senhaHash = passwordService.encode(usuarioAtualizacao.getSenha());

        usuarioAtualizacao.setId(usuarioEntidade.getId());
        usuarioAtualizacao.setLogin(usuarioEntidade.getLogin());

        return usuarioRepository.save(usuarioAtualizacao, senhaHash);
    }
}
