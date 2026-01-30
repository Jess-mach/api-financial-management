package br.com.ntt.usuario.infra.persistence;

import br.com.ntt.usuario.application.gateways.UsuarioRepository;
import br.com.ntt.usuario.domain.entity.Usuario;
import br.com.ntt.usuario.domain.exception.ResourceNotFoundException;
import br.com.ntt.usuario.infra.persistence.entity.UsuarioJpaEntity;
import br.com.ntt.usuario.infra.persistence.mapper.UsuarioJpaMapper;
import br.com.ntt.usuario.infra.persistence.repository.RepositoryJpa;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Slf4j
@Repository
public class UsuarioRepositoryInfra implements UsuarioRepository {

    private final RepositoryJpa repositoryJpa;
    private final UsuarioJpaMapper mapper;

    public UsuarioRepositoryInfra(RepositoryJpa repositoryJpa, UsuarioJpaMapper mapper) {
        this.repositoryJpa = repositoryJpa;
        this.mapper = mapper;
    }

    @Override
    public Usuario save(Usuario usuario, String senhaHash) {
        log.info("salvando usuario na base de dados - inicio");

        UsuarioJpaEntity entity = mapper.toEntity(usuario, senhaHash);
        UsuarioJpaEntity salva = repositoryJpa.save(entity);

        log.info("salvando usuario na base de dados - fim");

        return mapper.toDomain(salva);
    }

    @Override
    public Usuario findById(UUID id) {
        log.info("consultando usuario {}", id);

        UsuarioJpaEntity byId = repositoryJpa.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario não encontrado"));

        return mapper.toDomain(byId);
    }

    @Override
    public boolean existsByEmail(String email) {
        log.info("validação do email do usuario");

        return repositoryJpa.existsByEmail(email);
    }

    @Override
    public List<Usuario> findAll() {
        log.info("carregando listagem de usuarios");

        return repositoryJpa.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(UUID id) {
        log.info("efetivando exclusão do usuario");

        if (repositoryJpa.existsById(id)) {
            repositoryJpa.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Usuário não encontrado");
        }
    }
}
