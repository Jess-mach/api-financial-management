package br.com.ntt.usuario.infra.controller.dto;

import br.com.ntt.usuario.domain.PerfilUsuario;

import java.util.UUID;

public record UsuarioDto(

        UUID id,
        String nome,
        String email,
        String login,
        PerfilUsuario perfilUsuario) {
}
