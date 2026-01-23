package com.api.financial.management.application.usecase;

import com.api.financial.management.config.PasswordService;
import com.api.financial.management.domain.entity.Usuario;
import com.api.financial.management.application.gateways.UsuarioRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
