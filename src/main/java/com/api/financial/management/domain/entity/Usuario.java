package com.api.financial.management.domain.entity;

import java.util.Objects;
import java.util.UUID;

public class Usuario {

    private final  UUID id;
    private String nome;
    private String email;
    private DadosAutenticacao autenticacao;

    public Usuario(UUID id, String name, String email, DadosAutenticacao autenticacao) {
        this.id = id;
        this.nome = name;
        this.email = email;
        this.autenticacao = autenticacao;
        this.validar();
    }

    private void validar() {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Usuario user = (Usuario) obj;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String getLogin() {
        return autenticacao.login();
    }

}
