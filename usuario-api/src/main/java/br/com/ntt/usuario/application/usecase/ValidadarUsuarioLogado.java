package br.com.ntt.usuario.application.usecase;

import br.com.ntt.usuario.domain.PerfilUsuario;
import br.com.ntt.usuario.domain.entity.Usuario;
import br.com.ntt.usuario.domain.exception.AccessDeniedException;
import br.com.ntt.usuario.domain.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class ValidadarUsuarioLogado {

    private final List<PerfilUsuario> ehTipoUsuarioGerenteOuAdministrador = List.of(PerfilUsuario.GERENTE, PerfilUsuario.ADMINISTRADOR);

    public void validaUsuarioGerenteOuAdministrador(Usuario usuarioLogado) {
        if (ehTipoUsuarioGerenteOuAdministrador(usuarioLogado)) {
            log.info("usuário gerente ou administrador");
            return;
        }

        log.error("Erro de Acesso - Usuário sem permissão de Gerente/Administrador");
        throw new AccessDeniedException("Usuário sem permissão de Gerente/Administrador");
    }

    public void validaPermissaoDeCriarUsuarios(Usuario novoUsuario, Usuario usuarioLogado) {
        if (usuarioLogado == null) {
            if (!novoUsuario.getPerfilUsuario().equals(PerfilUsuario.USUARIO)) {
                log.warn("usuario sendo cadastrado sem token e com perfil Administrador ou Gerente");

                throw new BusinessException("Cadastre um usuário com perfil USUARIO");
            }
        } else {

            switch (novoUsuario.getPerfilUsuario()) {
                case ADMINISTRADOR:
                    if (!usuarioLogado.getPerfilUsuario().equals(PerfilUsuario.ADMINISTRADOR)) {
                        throw new BusinessException("Cadastre um usuário com perfil USUARIO");
                    }
                    break;

                case GERENTE: {
                    if (!ehTipoUsuarioGerenteOuAdministrador.contains(usuarioLogado.getPerfilUsuario())) {
                        throw new BusinessException("Cadastre um usuário com perfil USUARIO");
                    }
                    break;
                }

                case USUARIO: {
                    log.info("usuario comum");
                    break;
                }

                default: {
                    log.warn("novo usuario invalido");
                    throw new BusinessException("Cadastre um usuário com perfil USUARIO");
                }
            }
        }
    }

    public void validaSeEhProprioUsuarioLogadoOuUsuarioGerenteOuAdministrador(UUID id, Usuario usuarioLogado) {
        if (!usuarioLogado.getId().equals(id) && !ehTipoUsuarioGerenteOuAdministrador(usuarioLogado)) {
            log.warn("Erro de Acesso - Usuário sem permissão");
            throw new AccessDeniedException("Usuário sem permissão");
        }
    }

    private boolean ehTipoUsuarioGerenteOuAdministrador(Usuario usuarioLogado) {
        return ehTipoUsuarioGerenteOuAdministrador.contains(usuarioLogado.getPerfilUsuario());
    }
}
