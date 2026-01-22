package com.api.financial.management.domain.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UsuarioTest {

    private final ValidadorUsuario validador = new ValidadorUsuario();


    @Test
    void deveCriarUsuarioValido() {
        Usuario usuario = Usuario.criarNovo(
                "Jessica",
                "jessica@email.com",
                "jessica.dev",
                "SenhaForte@123",
                PerfilUsuario.ADMINISTRADOR);

        Assertions.assertDoesNotThrow(()-> validador.validar(usuario));

        Assertions.assertNotNull(usuario.getId());
        Assertions.assertEquals("Jessica", usuario.getNome());
        Assertions.assertEquals("jessica.dev", usuario.getLogin());
        Assertions.assertEquals("SenhaForte@123", usuario.getSenha());
        Assertions.assertEquals(PerfilUsuario.ADMINISTRADOR, usuario.getPerfilUsuario());
    }

    @Test
    void naoDeveCriarUsuarioSemNome() {
        IllegalArgumentException erro = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Usuario.criarNovo(
                    "",
                    "jessica@email.com",
                    "jessica.dev",
                    "SenhaForte@123",
                    PerfilUsuario.USUARIO);
        });

        Assertions.assertEquals("O nome é obrigatório", erro.getMessage());
    }

    @Test
    void naoDeveCriarUsuarioComEmailInvalido() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Usuario.criarNovo(
                    "Teste",
                    null,
                    "user",
                    "123456",
                    PerfilUsuario.GERENTE);
        });
    }

    @Test
    void deveAtualizarNomeEEmailCorretamente() {
        Usuario usuario = Usuario.criarNovo(
                "Nome Antigo",
                "old@email.com",
                "login",
                "SenhaForte@123",
                PerfilUsuario.USUARIO);

        usuario.atualizar("Nome Novo", "new@email.com");

        Assertions.assertEquals("Nome Novo", usuario.getNome());
        Assertions.assertEquals("new@email.com", usuario.getEmail());
    }

    @Test
    void naoDeveAtualizarParaNomeVazio() {
        Usuario usuario = Usuario.criarNovo(
                "Jessica",
                "email@email.com",
                "login",
                "senha123",
                PerfilUsuario.USUARIO);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            usuario.atualizar("", "novo@email.com");
        });

        Assertions.assertEquals("Jessica", usuario.getNome());
    }

    @Test
    void naoDeveCriarUsuarioComSenhaFraca() {
        Usuario usuario = Usuario.criarNovo(
                "Jessica",
                "email@email.com",
                "login",
                "123",
                PerfilUsuario.USUARIO
        );

        IllegalArgumentException erro = Assertions.assertThrows
                (IllegalArgumentException.class,
                        () -> validador.validar(usuario));

        Assertions.assertEquals("A senha deve conter pelo menos 8 caracteres, uma letra maiúscula," +
                "\"uma letra minúscula, um número e um caractere especial", erro.getMessage());
    }

    @Test
    void naoDeveCriarUsuarioSemLogin() {
        Usuario usuario = Usuario.criarNovo(
                "Jessica",
                "jessica@email.com",
                null, // Login nulo
                "SenhaForte@123",
                PerfilUsuario.ADMINISTRADOR
        );
    }

}

