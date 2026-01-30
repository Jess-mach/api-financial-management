package br.com.ntt.usuario.application.usecase;


import br.com.ntt.usuario.application.gateways.RepositorioDeUsuario;
import br.com.ntt.usuario.application.gateways.RepositorioDeEncriptacao;
import br.com.ntt.usuario.domain.PerfilUsuario;
import br.com.ntt.usuario.domain.entity.Usuario;
import br.com.ntt.usuario.domain.exception.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CriarUsuarioTest {
    
    @Mock
    private RepositorioDeUsuario repositorioDeUsuario;

    @Mock
    private RepositorioDeEncriptacao repositorioDeEncriptacao;

    @InjectMocks
    private CriarUsuario criarUsuario;

    @Test
    @DisplayName("Deve salvar usuário quando o e-mail não existe")
    void deveSalvarUsuarioComSucesso() {

        Usuario usuario = new Usuario(
                UUID.randomUUID(),
                "Jessica",
                "jessica@email.com",
                "jess.login",
                "Senha@123",
                PerfilUsuario.USUARIO);
        when(repositorioDeUsuario.existsByEmail(usuario.getEmail())).thenReturn(false);
        when(repositorioDeEncriptacao.encode("Senha@123")).thenReturn("hash_seguro");

        criarUsuario.executar(usuario);

        verify(repositorioDeEncriptacao).encode("Senha@123");
        verify(repositorioDeUsuario).save(usuario, "hash_seguro");
    }

    @Test
    @DisplayName("Deve lançar exceção quando o e-mail já está cadastrado")
    void deveLancarExcecaoEmailDuplicado() {
        Usuario usuario = new Usuario(
                UUID.randomUUID(),
                "Jessica",
                "jessica@email.com",
                "jess.login",
                "Senha@123",
                PerfilUsuario.USUARIO);
        when(repositorioDeUsuario.existsByEmail(anyString())).thenReturn(true);

        assertThrows(BusinessException.class, () -> criarUsuario.executar(usuario));
        verify(repositorioDeUsuario, never()).save(any(), anyString());
    }
}
