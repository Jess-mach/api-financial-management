package br.com.ntt.usuario.application.usecase;

import br.com.ntt.usuario.domain.entity.Usuario;
import br.com.ntt.usuario.application.gateways.UsuarioRepository;

import java.util.List;


public class ListarUsuario {

    private final UsuarioRepository usuarioRepository;

    public ListarUsuario(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> executar(){
        return usuarioRepository.findAll();
    }
}
