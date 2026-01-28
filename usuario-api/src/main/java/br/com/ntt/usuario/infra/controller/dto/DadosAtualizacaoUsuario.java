package br.com.ntt.usuario.infra.controller.dto;

import br.com.ntt.usuario.domain.PerfilUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DadosAtualizacaoUsuario(

        @NotBlank(message = "O nome é obrigatório")
        String nome,

        @NotBlank(message = "O email é obrigatório")
        @Email
        String email,

        @NotBlank(message = "A senha é obrigatória")
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$",
                message = "A senha deve conter pelo menos 8 caracteres, uma letra maiúscula, " +
                        "uma letra minúscula, um número e um caractere especial")
        String senha,

        @NotNull(message = "O perfil é obrigatório")
        PerfilUsuario perfilUsuario) {
}
