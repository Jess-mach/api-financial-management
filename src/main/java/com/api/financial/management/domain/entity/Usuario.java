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

    public static Usuario criarNovo(String nome, String email, DadosAutenticacao auth) {
        ValidadorUsuario.validarCadastro(nome, email, auth);
        return new Usuario(UUID.randomUUID(), nome, email, auth);
    }


    public static Usuario restaurar(UUID id, String nome, String email, DadosAutenticacao auth) {
        return new Usuario(id, nome, email, auth);
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

}
