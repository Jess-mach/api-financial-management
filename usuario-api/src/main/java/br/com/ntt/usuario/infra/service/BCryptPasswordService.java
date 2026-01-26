package br.com.ntt.usuario.infra.service;

import br.com.ntt.usuario.config.PasswordService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class BCryptPasswordService implements PasswordService {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public String encode(String senha) {
        return encoder.encode(senha);
    }
}
