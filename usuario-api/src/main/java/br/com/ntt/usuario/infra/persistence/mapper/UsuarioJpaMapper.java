package br.com.ntt.usuario.infra.persistence.mapper;

import br.com.ntt.usuario.infra.controller.dto.DadosAtualizacaoUsuario;
import br.com.ntt.usuario.infra.controller.dto.DadosCadastroUsuario;
import br.com.ntt.usuario.infra.controller.dto.UsuarioDto;
import ch.qos.logback.core.util.StringUtil;
import br.com.ntt.usuario.domain.entity.Usuario;
import br.com.ntt.usuario.infra.persistence.entity.UsuarioJpaEntity;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;

@Component
public class UsuarioJpaMapper {

    public UsuarioJpaEntity toEntity(Usuario usuario, String senhaHash){
        return new UsuarioJpaEntity(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getLogin(),
                !StringUtil.isNullOrEmpty(senhaHash) ? senhaHash : usuario.getSenha(),
                usuario.getPerfilUsuario()
        );
    }

    public Usuario toDomain(UsuarioJpaEntity entity){
        return Usuario.restaurar(
                entity.getId(),
                entity.getNome(),
                entity.getEmail(),
                entity.getLogin(),
                null,
                entity.getPerfilUsuario()
        );
    }

}
