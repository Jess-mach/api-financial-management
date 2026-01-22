package com.api.financial.management.infra.config;

import com.api.financial.management.application.usecase.AtualizarUsuario;
import com.api.financial.management.application.usecase.CriarUsuario;
import com.api.financial.management.application.usecase.ListarUsuario;
import com.api.financial.management.domain.repository.UsuarioRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UsuarioConfig {

    @Bean
    public CriarUsuario criarUsuario(UsuarioRepository usuarioRepository){
        return new CriarUsuario(usuarioRepository);
    }

    @Bean
    public ListarUsuario listarUsuario(UsuarioRepository usuarioRepository) {
        return new ListarUsuario(usuarioRepository);
    }

    @Bean
    public AtualizarUsuario atualizarUsuario(UsuarioRepository usuarioRepository) {
        return new AtualizarUsuario(usuarioRepository);
    }

}
