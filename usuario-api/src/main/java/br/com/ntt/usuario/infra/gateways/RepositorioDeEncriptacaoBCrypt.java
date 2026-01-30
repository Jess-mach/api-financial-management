package br.com.ntt.usuario.infra.gateways;

import br.com.ntt.usuario.application.gateways.RepositorioDeEncriptacao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RepositorioDeEncriptacaoBCrypt implements RepositorioDeEncriptacao {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public String encode(String senha) {
        log.info("iniciando codificação da senha");

        return encoder.encode(senha);
    }
}
