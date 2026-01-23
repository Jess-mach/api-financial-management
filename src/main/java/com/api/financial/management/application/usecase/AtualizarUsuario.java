package com.api.financial.management.application.usecase;

import com.api.financial.management.application.gateways.UsuarioRepository;
import com.api.financial.management.config.PasswordService;
import com.api.financial.management.domain.entity.Usuario;


public class AtualizarUsuario {

    private final UsuarioRepository usuarioRepository;

    private final PasswordService passwordService;

    public AtualizarUsuario(UsuarioRepository usuarioRepository, PasswordService passwordService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordService = passwordService;
    }

    public Usuario executar(Usuario usuarioAtualizacao) {
        Usuario usuarioEntidade = usuarioRepository.findById(usuarioAtualizacao.getId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        String senhaHash = passwordService.encode(usuarioAtualizacao.getSenha());

        return usuarioRepository.save(usuarioEntidade, senhaHash);
    }
}
