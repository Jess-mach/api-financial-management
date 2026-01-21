package com.api.financial.management.domain.entity;

public class ValidadorUsuario {

    public static void validarCadastro(String nome, String email, DadosAutenticacao dadosAutenticacao) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email não pode ser vazio");
        }
    }

    public static void validarAtualizacao(String novoNome, String novoEmail) {
        if (novoNome == null || novoNome.isBlank()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
        if (novoEmail == null || novoEmail.isBlank()) {
            throw new IllegalArgumentException("Email não pode ser vazio");
        }
    }
}
