package br.com.alura.codechella;

import br.com.alura.codechella.domain.entities.transacao.Transacao;
import br.com.alura.codechella.infra.gateways.RepositorioDeTransacaoEmArquivo;

import java.time.LocalDate;

public class UtilizaUsuarioComArquivos {
    public static void main(String[] args) {
        RepositorioDeTransacaoEmArquivo repositorioDeUsuarioEmArquivo = new RepositorioDeTransacaoEmArquivo();

        repositorioDeUsuarioEmArquivo.cadastrarUsuario(new Transacao("456.789.888-88", "João",
                LocalDate.parse("2000-10-15"), "joao@email.com"));
        repositorioDeUsuarioEmArquivo.cadastrarUsuario(new Transacao("456.789.888-88", "Maria",
                LocalDate.parse("2000-10-15"), "maria@email.com"));
        repositorioDeUsuarioEmArquivo.cadastrarUsuario(new Transacao("456.789.888-88", "Vinicios",
                LocalDate.parse("2000-10-15"), "vinicios@email.com"));
        repositorioDeUsuarioEmArquivo.cadastrarUsuario(new Transacao("456.789.888-88", "Raphael",
                LocalDate.parse("2000-10-15"), "raphael@email.com"));

        //System.out.println(repositorioDeUsuarioEmArquivo.listarTodos());
        repositorioDeUsuarioEmArquivo.gravaEmArquivo("usuarios.txt");
    }
}
