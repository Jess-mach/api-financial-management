package br.com.ntt.usuario.domain.entity;

import br.com.ntt.usuario.domain.PerfilUsuario;

import java.util.Objects;
import java.util.UUID;

public class Usuario {

    private final  UUID id;
    private String nome;
    private String email;
    private String login;
    private String senha;
    private PerfilUsuario perfilUsuario;

    public Usuario(UUID id, String name, String email, String login, String senha, PerfilUsuario perfilUsuario) {
        this.id = id;
        this.nome = name;
        this.email = email;
        this.login = login;
        this.senha = senha;
        this.perfilUsuario = perfilUsuario;
        this.validar();
    }

    private void validar() {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome é obrigatório");
        }
    }

    public static Usuario criarNovo(String nome, String email, String login, String senha, PerfilUsuario perfilUsuario) {
        if (perfilUsuario == null) {
            perfilUsuario = PerfilUsuario.USUARIO;
        }
        return new Usuario(UUID.randomUUID(), nome, email, login, senha, perfilUsuario);
    }


    public static Usuario restaurar(UUID id, String nome, String email, String login, String senha, PerfilUsuario perfilUsuario) {
        return new Usuario(id, nome, email, login, senha, perfilUsuario);
    }

    public static Usuario atualizarUsuario(String id, String nome, String email, String senha, PerfilUsuario perfilUsuario) {
        if (perfilUsuario == null) {
            perfilUsuario = PerfilUsuario.USUARIO;
        }
        return new Usuario(UUID.fromString(id), nome, email, null, senha, perfilUsuario);
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
        return login;
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

    public PerfilUsuario getPerfilUsuario() {
        return perfilUsuario;
    }

    public String getSenha() {
        return senha;
    }


}
