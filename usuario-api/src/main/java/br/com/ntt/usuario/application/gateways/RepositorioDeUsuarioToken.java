package br.com.ntt.usuario.application.gateways;

public interface RepositorioDeUsuarioToken {
    String gerarToken( String login, String senha);
}
