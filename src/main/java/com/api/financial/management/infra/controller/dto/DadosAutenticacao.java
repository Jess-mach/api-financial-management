package com.api.financial.management.infra.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DadosAutenticacao(

        @NotBlank(message = "Usuário ou senha inválidos")
        String login,

        @NotBlank(message = "Usuário ou senha inválidos")
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$",
                message = "Usuário ou senha inválidos")
        String senha) {
}