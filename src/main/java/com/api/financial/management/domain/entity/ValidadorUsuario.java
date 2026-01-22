package com.api.financial.management.domain.entity;

public class ValidadorUsuario {

    public static void validarAtualizacao(String novoNome, String novoEmail) {
        if (novoNome == null || novoNome.isBlank()) {
            throw new IllegalArgumentException("O nome é obrigatório");
        }
        if (novoEmail == null || novoEmail.isBlank()) {
            throw new IllegalArgumentException("O email é obrigatório");
        }
    }

    public static void validar(Usuario usuario) {
        if (usuario.getEmail() == null || !usuario.getEmail().contains("@")) {
            throw new IllegalArgumentException("Email inválido");
        }
        if(usuario.getSenha().length() < 8){
            throw new IllegalArgumentException("A senha deve conter pelo menos 8 caracteres, uma letra maiúscula," +
                    "\"uma letra minúscula, um número e um caractere especial");
        }
    }
}
