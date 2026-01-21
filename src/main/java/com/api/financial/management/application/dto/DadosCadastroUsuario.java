package com.api.financial.management.application.dto;

import com.api.financial.management.domain.entity.PerfilUsuario;

public record DadosCadastroUsuario(
        String nome,
        String email,
        String login,
        String senha,
        PerfilUsuario perfilUsuario ) {
}
