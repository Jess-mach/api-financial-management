package com.api.financial.management.application.usecase;

import com.api.financial.management.application.gateways.UsuarioRepository;

import java.util.UUID;

public class DeletarUsuario {

    private final UsuarioRepository usuarioRepository;

    public DeletarUsuario(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void executar(UUID id) {
        usuarioRepository.deleteById(id);
    }
}
