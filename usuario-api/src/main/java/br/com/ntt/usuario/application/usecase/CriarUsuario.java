package br.com.ntt.usuario.application.usecase;

import br.com.ntt.usuario.config.PasswordService;
import br.com.ntt.usuario.domain.entity.Usuario;
import br.com.ntt.usuario.application.gateways.UsuarioRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class CriarUsuario {

    private final UsuarioRepository usuarioRepository;

    private final PasswordService passwordService;

    public CriarUsuario(UsuarioRepository usuarioRepository, PasswordService passwordService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordService = passwordService;
    }

    public Usuario executar(Usuario novoUsuario) {
        if (usuarioRepository.existsByEmail(novoUsuario.getEmail())) {
            throw new IllegalArgumentException("E-mail inválido.");
        }

        String senhaHash = passwordService.encode(novoUsuario.getSenha());

        return usuarioRepository.save(novoUsuario, senhaHash);
    }

    @Transactional
    public List<Usuario> lote(List<Usuario> usuarios) {
        List<Usuario> listaUsuariosSalvos = new ArrayList<>();
        for (Usuario user : usuarios) {
            Usuario usuarioSalvo = executar(user);
            listaUsuariosSalvos.add(usuarioSalvo);
        }
        return usuarios;
    }
}
