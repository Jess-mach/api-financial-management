package br.com.ntt.usuario.application.usecase;


import br.com.ntt.usuario.application.gateways.UsuarioRepository;
import br.com.ntt.usuario.config.PasswordService;
import br.com.ntt.usuario.domain.PerfilUsuario;
import br.com.ntt.usuario.domain.entity.Usuario;
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
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordService passwordService;

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
        when(usuarioRepository.existsByEmail(usuario.getEmail())).thenReturn(false);
        when(passwordService.encode("Senha@123")).thenReturn("hash_seguro");

        criarUsuario.executar(usuario);

        verify(passwordService).encode("Senha@123");
        verify(usuarioRepository).save(usuario, "hash_seguro");
    }

    @Test
    @DisplayName("Deve lançar exceção quando o e-mail já está cadastrado")
    void deveLancarExcecaoEmailDuplicado() {
        // Arrange
        Usuario usuario = new Usuario(
                UUID.randomUUID(),
                "Jessica",
                "jessica@email.com",
                "jess.login",
                "Senha@123",
                PerfilUsuario.USUARIO);
        when(usuarioRepository.existsByEmail(anyString())).thenReturn(true);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> criarUsuario.executar(usuario));
        verify(usuarioRepository, never()).save(any(), anyString());
    }
}
