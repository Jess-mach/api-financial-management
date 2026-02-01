package br.com.ntt.usuario.application.gateways;

import br.com.ntt.usuario.domain.entity.Usuario;

import java.util.List;
import java.util.UUID;

public interface RepositorioDeUsuario {

    Usuario save (Usuario usuario, String senhaHash);
    Usuario findById(UUID id);
    boolean existsByEmail(String email);
    List<Usuario> findAll();
    void deleteById(UUID id);
    boolean existsByLogin(String login);

}

