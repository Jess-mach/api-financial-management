package br.com.ntt.usuario.infra.persistence.repository;

import br.com.ntt.usuario.infra.persistence.entity.UsuarioJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface RepositoryJpa extends JpaRepository<UsuarioJpaEntity, UUID> {
    boolean existsByEmail(String email);
    UserDetails findByLogin(String login);
}
