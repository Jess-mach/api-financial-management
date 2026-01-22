package com.api.financial.management.domain.entity;

import java.util.Objects;
import java.util.UUID;

public class Usuario {

    private final  UUID id;
    private String nome;
    private String email;
    private DadosAutenticacao autenticacao;
    private PerfilUsuario perfilUsuario;

    public Usuario(UUID id, String name, String email, DadosAutenticacao autenticacao, PerfilUsuario perfilUsuario) {
        this.id = id;
        this.nome = name;
        this.email = email;
        this.autenticacao = autenticacao;
        this.perfilUsuario = perfilUsuario;
        this.validar();
    }

    private void validar() {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
    }

    public static Usuario criarNovo(String nome, String email, DadosAutenticacao autenticacao, PerfilUsuario perfilUsuario) {
        ValidadorUsuario.validarCadastro(nome, email, autenticacao);
        if (perfilUsuario == null) {
            perfilUsuario = PerfilUsuario.USUARIO;
        }
        return new Usuario(UUID.randomUUID(), nome, email, autenticacao, perfilUsuario);
    }


    public static Usuario restaurar(UUID id, String nome, String email, DadosAutenticacao autenticacao, PerfilUsuario perfilUsuario) {
        return new Usuario(id, nome, email, autenticacao, perfilUsuario);
    }


    public void atualizar(String novoNome, String novoEmail) {
        ValidadorUsuario.validarAtualizacao(novoNome, novoEmail);
        this.nome = novoNome;
        this.email = novoEmail;
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
        return autenticacao.senha();
    }
}
