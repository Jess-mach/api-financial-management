package com.api.financial.management.application.usecase;

import com.api.financial.management.domain.entity.Usuario;
import com.api.financial.management.application.gateways.UsuarioRepository;

public class AtualizarUsuario {

    private final UsuarioRepository usuarioRepository;

    public AtualizarUsuario(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario executar(Usuario usuarioAtualizacao) {
        Usuario usuarioEntidade = usuarioRepository.findById(usuarioAtualizacao.getId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        return usuarioRepository.save(usuarioEntidade);
    }
}
