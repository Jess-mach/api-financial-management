package com.api.financial.management.application.usecase;

import com.api.financial.management.domain.entity.Usuario;
import com.api.financial.management.application.gateways.UsuarioRepository;

public class CriarUsuario {

    private final UsuarioRepository usuarioRepository;

    public CriarUsuario(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario executar(Usuario novoUsuario) {
        if (usuarioRepository.existsByEmail(novoUsuario.getEmail())) {
            throw new IllegalArgumentException("Já existe um usuário com este e-mail.");
        }

        return usuarioRepository.save(novoUsuario);
    }
}
