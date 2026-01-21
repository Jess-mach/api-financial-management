package com.api.financial.management.domain.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UsuarioTest {

    @Test
    void deveCriarUsuarioValido() {
        DadosAutenticacao auth = new DadosAutenticacao("jessica.dev", "senha123");

        Usuario usuario = Usuario.criarNovo("Jessica", "jessica@email.com", auth);

        Assertions.assertNotNull(usuario.getId()); // Garante que gerou ID
        Assertions.assertEquals("Jessica", usuario.getNome());
        Assertions.assertEquals("jessica.dev", usuario.getLogin());
    }

    @Test
    void naoDeveCriarUsuarioSemNome() {
        DadosAutenticacao auth = new DadosAutenticacao("jessica.dev", "senha123");

        IllegalArgumentException erro = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Usuario.criarNovo("", "jessica@email.com", auth);
        });

        Assertions.assertEquals("Nome não pode ser vazio", erro.getMessage());
    }

    @Test
    void naoDeveCriarUsuarioComEmailInvalido() {
        DadosAutenticacao auth = new DadosAutenticacao("user", "123456");

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Usuario.criarNovo("Teste", null, auth); // Email nulo
        });
    }

    @Test
    void deveAtualizarNomeEEmailCorretamente() {

        DadosAutenticacao auth = new DadosAutenticacao("login", "senha123");
        Usuario usuario = Usuario.criarNovo("Nome Antigo", "old@email.com", auth);

        usuario.atualizar("Nome Novo", "new@email.com");

        Assertions.assertEquals("Nome Novo", usuario.getNome());
        Assertions.assertEquals("new@email.com", usuario.getEmail());
    }

    @Test
    void naoDeveAtualizarParaNomeVazio() {
        DadosAutenticacao auth = new DadosAutenticacao("login", "senha123");
        Usuario usuario = Usuario.criarNovo("Jessica", "email@email.com", auth);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            usuario.atualizar("", "novo@email.com");
        });

        Assertions.assertEquals("Jessica", usuario.getNome());
    }
}

