package br.com.ntt.usuario.application.usecase;

import br.com.ntt.usuario.application.gateways.UsuarioRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DeletarUsuario {

    private final UsuarioRepository usuarioRepository;

    public DeletarUsuario(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void executar(UUID id) {
        usuarioRepository.deleteById(id);
    }
}
