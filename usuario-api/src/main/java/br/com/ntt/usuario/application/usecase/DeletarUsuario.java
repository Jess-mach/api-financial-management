package br.com.ntt.usuario.application.usecase;

import br.com.ntt.usuario.application.gateways.UsuarioRepository;

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
