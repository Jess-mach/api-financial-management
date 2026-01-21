package com.api.financial.management.domain.entity;

import java.util.UUID;

public class Usuario {

    private final UUID id;
    private final String nome;
    private final String email;
    private final String login;
    private final String senha;

    public Usuario(UUID id, String nome, String email, String login, String senha) {

        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }

        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email não pode ser vazio");
        }

        if (login == null || login.isBlank()) {
            throw new IllegalArgumentException("Login não pode ser vazio");
        }

        this.id = UUID.randomUUID();
        this.nome = nome;
        this.email = email;
        this.login = login;
        this.senha = senha;
    }
}
