package com.api.financial.management.config;

import com.api.financial.management.application.usecase.AtualizarUsuario;
import com.api.financial.management.application.usecase.CriarUsuario;
import com.api.financial.management.application.usecase.DeletarUsuario;
import com.api.financial.management.application.usecase.ListarUsuario;
import com.api.financial.management.application.gateways.UsuarioRepository;
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

    @Bean
    public DeletarUsuario deletarUsuario(UsuarioRepository usuarioRepository) {
        return new DeletarUsuario(usuarioRepository);
    }

}
