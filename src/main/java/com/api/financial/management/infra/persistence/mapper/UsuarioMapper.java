package com.api.financial.management.infra.persistence.mapper;

import com.api.financial.management.domain.entity.Usuario;
import com.api.financial.management.infra.persistence.entity.UsuarioJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public UsuarioJpaEntity toEntity(Usuario usuario){
        return new UsuarioJpaEntity(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getLogin(),
                usuario.getSenha(),
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
