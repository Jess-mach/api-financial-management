package br.com.ntt.usuario.application.usecase;

import br.com.ntt.usuario.config.PasswordService;
import br.com.ntt.usuario.domain.entity.Usuario;
import br.com.ntt.usuario.application.gateways.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class CriarUsuario {

    private final UsuarioRepository usuarioRepository;

    private final PasswordService passwordService;

    public CriarUsuario(UsuarioRepository usuarioRepository, PasswordService passwordService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordService = passwordService;
    }

    public Usuario executar(Usuario novoUsuario) {
        log.info("criação de usuario - inicio");
        if (usuarioRepository.existsByEmail(novoUsuario.getEmail())) {
            log.info("cadastro de usuario - email ja cadastrado na base");

            throw new IllegalArgumentException("E-mail inválido.");
        }

        String senhaHash = passwordService.encode(novoUsuario.getSenha());

        log.info("criação de usuario - fim");

        return usuarioRepository.save(novoUsuario, senhaHash);
    }

    @Transactional
    public List<Usuario> lote(List<Usuario> usuarios) {
        log.info("Iniciando lote de criação de usuários");
        List<Usuario> listaUsuariosSalvos = new ArrayList<>();
        for (Usuario user : usuarios) {
            Usuario usuarioSalvo = executar(user);
            listaUsuariosSalvos.add(usuarioSalvo);
        }
        log.info("finalizando lote de criação de usuários");

        return usuarios;
    }
}
