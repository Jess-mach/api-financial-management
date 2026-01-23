package br.com.alura.codechella.domain.entities.transacao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class TransacaoTest {
    @Test
    public void naoDeveCadastrarUsuarioComCpfNoFormatoInvalido(){
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Transacao("123456.789-99", "Jacque", LocalDate.parse("1990-09-08"), "email@email.com"));

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Transacao("12345678999", "Jacque", LocalDate.parse("1990-09-08"), "email@email.com"));


        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Transacao("", "Jacque", LocalDate.parse("1990-09-08"), "email@email.com"));

    }

    @Test
    public void deveCriarUsuarioUsandoFabricaDeUsuario(){
        FabricaDeTransacao fabrica = new FabricaDeTransacao();
        Transacao transacao = fabrica.comNomeCpfNascimento("Emily", "654.123.897-88",
                LocalDate.parse("2000-10-01"));

        Assertions.assertEquals("Emily", transacao.getNome());

        transacao = fabrica.incluiEndereco("12345-999", 40, "apto 201");

        Assertions.assertEquals("apto 201", transacao.getEndereco().getComplemento());

    }
}
