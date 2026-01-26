package br.com.ntt.usuario.infra.persistence.mapper;

import ch.qos.logback.core.util.StringUtil;
import br.com.ntt.usuario.domain.entity.Usuario;
import br.com.ntt.usuario.infra.persistence.entity.UsuarioJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

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
