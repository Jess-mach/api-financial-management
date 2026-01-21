package com.api.financial.management.domain.repository;

import com.api.financial.management.domain.entity.Usuario;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository {

    Usuario save (Usuario usuario);
    Optional<Usuario> findById(UUID id);
    Optional<Usuario> findByEmail(String email);
    List<Usuario> findAll();
    void deleteById(UUID id);
    boolean existsByEmail(String email);
}
