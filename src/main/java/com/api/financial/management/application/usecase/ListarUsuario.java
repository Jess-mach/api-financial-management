package com.api.financial.management.application.usecase;

import com.api.financial.management.domain.entity.Usuario;
import com.api.financial.management.application.gateways.UsuarioRepository;

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
