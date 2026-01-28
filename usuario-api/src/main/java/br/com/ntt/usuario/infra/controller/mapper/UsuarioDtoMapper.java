package br.com.ntt.usuario.infra.controller.mapper;

import br.com.ntt.usuario.domain.entity.Usuario;
import br.com.ntt.usuario.infra.controller.dto.DadosAtualizacaoUsuario;
import br.com.ntt.usuario.infra.controller.dto.DadosCadastroUsuario;
import br.com.ntt.usuario.infra.controller.dto.UsuarioDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;

@Component
public class UsuarioDtoMapper {

    public Usuario toDomain(DadosCadastroUsuario dados){
        return new Usuario(
                null,
                dados.nome(),
                dados.email(),
                dados.login(),
                dados.senha(),
                dados.perfilUsuario()
        );
    }

    public Usuario toDomain(String id, DadosAtualizacaoUsuario dados) {
        return Usuario.atualizarUsuario(
                id,
                dados.nome(),
                dados.email(),
                dados.senha(),
                dados.perfilUsuario()
        );
    }


    public UsuarioDto toDto(@Valid Usuario dados) {
        return new UsuarioDto(
                dados.getId(),
                dados.getNome(),
                dados.getEmail(),
                dados.getLogin(),
                dados.getPerfilUsuario()
        );
    }

}
