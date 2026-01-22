package com.api.financial.management.infra.persistence.entity;

import com.api.financial.management.domain.entity.PerfilUsuario;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "usuarios")
public class UsuarioJpaEntity {

    @Id
    private UUID id;
    private String nome;
    private String email;
    private String login;
    private String senha;

    @Enumerated(EnumType.STRING)
    private PerfilUsuario perfilUsuario;

    public UsuarioJpaEntity() {}

    public UsuarioJpaEntity(UUID id, String nome, String email, String login, String senha, PerfilUsuario perfilUsuario) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.login = login;
        this.senha = senha;
        this.perfilUsuario = perfilUsuario;

    }

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public PerfilUsuario getPerfilUsuario() {
        return perfilUsuario;
    }
}

