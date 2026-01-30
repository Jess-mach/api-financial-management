package br.com.ntt.usuario.infra.service;

import br.com.ntt.usuario.config.PasswordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Slf4j
@Component
public class BCryptPasswordService implements PasswordService {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public String encode(String senha) {
        log.info("iniciando codificação da senha");

        return encoder.encode(senha);
    }
}
