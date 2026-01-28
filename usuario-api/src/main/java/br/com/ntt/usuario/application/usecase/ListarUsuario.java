package br.com.ntt.usuario.application.usecase;

import br.com.ntt.usuario.domain.entity.Usuario;
import br.com.ntt.usuario.application.gateways.UsuarioRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListarUsuario {

    private final UsuarioRepository usuarioRepository;

    public ListarUsuario(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> executar(){
        return usuarioRepository.findAll();
    }
}
