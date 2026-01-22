package com.api.financial.management.infra.config;

import com.api.financial.management.application.usecase.CriarUsuario;
import com.api.financial.management.domain.repository.UsuarioRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UsuarioConfig {

    @Bean
    public CriarUsuario criarUsuario(UsuarioRepository usuarioRepository){
        return new CriarUsuario(usuarioRepository);
    }
}
