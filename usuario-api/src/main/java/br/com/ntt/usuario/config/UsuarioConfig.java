package br.com.ntt.usuario.config;

import br.com.ntt.usuario.application.usecase.*;
import com.api.financial.management.application.usecase.*;
import br.com.ntt.usuario.application.gateways.UsuarioRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UsuarioConfig {

    @Bean
    public CriarUsuario criarUsuario(UsuarioRepository usuarioRepository, PasswordService passwordService){
        return new CriarUsuario(usuarioRepository, passwordService);
    }

    @Bean
    public ArquivoUsuario uploadUsuario(CriarUsuario criarUsuario){
        return new ArquivoUsuario(criarUsuario);
    }

    @Bean
    public ListarUsuario listarUsuario(UsuarioRepository usuarioRepository) {
        return new ListarUsuario(usuarioRepository);
    }

    @Bean
    public AtualizarUsuario atualizarUsuario(UsuarioRepository usuarioRepository, PasswordService passwordService) {
        return new AtualizarUsuario(usuarioRepository, passwordService);
    }

    @Bean
    public DeletarUsuario deletarUsuario(UsuarioRepository usuarioRepository) {
        return new DeletarUsuario(usuarioRepository);
    }

}
