package br.com.ntt.usuario.application.usecase;

import br.com.ntt.usuario.application.gateways.RepositorioDeAutenticacaoDeUsuario;
import br.com.ntt.usuario.domain.entity.Usuario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuthenticaUsuario {
    private final RepositorioDeAutenticacaoDeUsuario repositorioDeAutenticacaoDeUsuario;

    public AuthenticaUsuario(RepositorioDeAutenticacaoDeUsuario repositorioDeAutenticacaoDeUsuario) {
        this.repositorioDeAutenticacaoDeUsuario = repositorioDeAutenticacaoDeUsuario;
    }

    public String gerarToken(String login,  String senha) {
        return repositorioDeAutenticacaoDeUsuario.gerarToken(login, senha);
    }

    public Usuario recuperaUsuarioLogado(boolean podeSerNulo) {
        try {
            return repositorioDeAutenticacaoDeUsuario.recuperaUsuarioLogado();
        } catch (Exception e){
            if (podeSerNulo) {
                return null;
            }
            log.info("usuario nao recuperado");

            throw e;
        }
    }
}
