package br.com.ntt.usuario.application.usecase;

import br.com.ntt.usuario.application.gateways.RepositorioDeEncriptacao;
import br.com.ntt.usuario.domain.entity.Usuario;
import br.com.ntt.usuario.application.gateways.RepositorioDeUsuario;
import br.com.ntt.usuario.domain.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class CriarUsuario {

    private final RepositorioDeUsuario repositorioDeUsuario;

    private final RepositorioDeEncriptacao repositorioDeEncriptacao;

    public CriarUsuario(RepositorioDeUsuario repositorioDeUsuario, RepositorioDeEncriptacao repositorioDeEncriptacao) {
        this.repositorioDeUsuario = repositorioDeUsuario;
        this.repositorioDeEncriptacao = repositorioDeEncriptacao;
    }

    public Usuario executar(Usuario novoUsuario) {
        log.info("criação de usuario - inicio");

        if (repositorioDeUsuario.existsByEmail(novoUsuario.getEmail())) {
            log.info("cadastro de usuario - email ja cadastrado na base");

            throw new BusinessException("E-mail inválido.");
        }

        String senhaHash = repositorioDeEncriptacao.encode(novoUsuario.getSenha());

        log.info("criação de usuario - fim");

        return repositorioDeUsuario.save(novoUsuario, senhaHash);
    }

    @Transactional
    public List<Usuario> lote(List<Usuario> usuarios) {
        log.info("Iniciando lote de criação de usuários");
        List<Usuario> listaUsuariosSalvos = new ArrayList<>();
        for (Usuario user : usuarios) {
            Usuario usuarioSalvo = executar(user);
            listaUsuariosSalvos.add(usuarioSalvo);
        }
        log.info("finalizando lote de criação de usuários");

        return listaUsuariosSalvos;
    }
}
