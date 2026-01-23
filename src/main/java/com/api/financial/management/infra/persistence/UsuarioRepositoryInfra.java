package com.api.financial.management.infra.persistence;

import com.api.financial.management.domain.entity.Usuario;
import com.api.financial.management.application.gateways.UsuarioRepository;
import com.api.financial.management.infra.persistence.entity.UsuarioJpaEntity;
import com.api.financial.management.infra.persistence.mapper.UsuarioMapper;
import com.api.financial.management.infra.persistence.repository.RepositoryJpa;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UsuarioRepositoryInfra implements UsuarioRepository {

    private final RepositoryJpa repositoryJpa;
    private final UsuarioMapper mapper;

    public UsuarioRepositoryInfra(RepositoryJpa repositoryJpa, UsuarioMapper mapper) {
        this.repositoryJpa = repositoryJpa;
        this.mapper = mapper;
    }


    @Override
    public Usuario save(Usuario usuario, String senhaHash) {
        UsuarioJpaEntity entity = mapper.toEntity(usuario, senhaHash);
        UsuarioJpaEntity salva = repositoryJpa.save(entity);
        return mapper.toDomain(salva);
    }

    @Override
    public Optional<Usuario> findById(UUID id) {
        return repositoryJpa.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repositoryJpa.existsByEmail(email);
    }

    @Override
    public List<Usuario> findAll() {
        return repositoryJpa.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(UUID id) {
        if (repositoryJpa.existsById(id)) {
            repositoryJpa.deleteById(id);
        } else {
            throw new IllegalArgumentException("Usuário não encontrado");
        }
    }
}
