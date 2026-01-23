package com.api.financial.management.application.gateways;

import com.api.financial.management.domain.entity.Usuario;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository {

    Usuario save (Usuario usuario, String senhaHash);
    Optional<Usuario> findById(UUID id);
    boolean existsByEmail(String email);
    List<Usuario> findAll();
    void deleteById(UUID id);

}
