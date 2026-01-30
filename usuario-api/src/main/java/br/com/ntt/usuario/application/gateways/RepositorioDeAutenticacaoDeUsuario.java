package br.com.ntt.usuario.application.gateways;

import br.com.ntt.usuario.domain.entity.Usuario;

public interface RepositorioDeAutenticacaoDeUsuario {
    String gerarToken( String login, String senha);

    Usuario recuperaUsuarioLogado();
}
