package br.com.ntt.transacao.producer.application.usecases;

import br.com.ntt.common.transacao.domain.entity.Transacao;
import br.com.ntt.common.transacao.domain.exception.AccessDeniedException;
import br.com.ntt.common.transacao.domain.exception.BusinessException;
import br.com.ntt.common.transacao.domain.model.StatusTransacao;
import br.com.ntt.common.transacao.domain.model.TipoTransacao;
import br.com.ntt.transacao.producer.application.gateways.RepositorioDeTransacao;
import br.com.ntt.transacao.producer.domain.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListarTransacaoTest {
    @Mock
    private RepositorioDeTransacao repositorio;

    @InjectMocks
    private ListarTransacao listarTransacao;

    private final UUID usuarioId = UUID.randomUUID();
    private final Usuario administrador = new Usuario(
            UUID.randomUUID(),
            "Admin",
            "ADMINISTRADOR"
    );
    private final Usuario gerente = new Usuario(
            UUID.randomUUID(),
            "Gerente",
            "GERENTE"
    );
    private final Usuario usuarioComum = new Usuario(
            usuarioId,
            "Comum",
            "CLIENTE"
    );


    @Test
    @DisplayName("Sucesso na consulta")
    void deveConsultarComSucesso() {
        Transacao transacao = new Transacao(
                UUID.randomUUID(),
                usuarioId,
                new BigDecimal("150.00"),
                TipoTransacao.DEPOSITO,
                StatusTransacao.PENDENTE,
                LocalDateTime.now(),
                null,
                "BRL",
                BigDecimal.ONE,
                "Teste de transação",
                123456L,
                new BigDecimal("150.00")
        );

        when(repositorio.listarTodos(usuarioId)).thenReturn(List.of(transacao));

        List<Transacao> resultado = listarTransacao.listarTodos(usuarioId, usuarioComum);

        assertNotNull(resultado);
        verify(repositorio, times(1)).listarTodos(usuarioId);
    }

    @Test
    @DisplayName("Erro ao consultar no banco de dados")
    void erroConsultaTransacao() {
        when(repositorio.listarTodos(any()))
                .thenThrow(new BusinessException("Erro ao conectar no Postgres"));

        assertThrows(RuntimeException.class, () -> listarTransacao.listarTodos(usuarioId, usuarioComum));
    }

    @Test
    @DisplayName("Deve negar acesso para não-gerente sem ID de usuário")
    void acessoNegadoParaNaoGerenteSemId() {
        assertThrows(AccessDeniedException.class, () -> listarTransacao.listarTodos(null, usuarioComum));
    }

    @Test
    @DisplayName("Deve negar acesso para não-gerente com ID de usuário diferente")
    void acessoNegadoParaNaoGerenteComIdDiferente() {
        UUID outroUsuarioId = UUID.randomUUID();
        assertThrows(AccessDeniedException.class, () -> listarTransacao.listarTodos(outroUsuarioId, usuarioComum));
    }

    @Test
    @DisplayName("Deve permitir que o gerente acesse as transações de outro usuário")
    void sucessoParaGerenteComIdDiferente() {
        UUID outroUsuarioId = UUID.randomUUID();
        when(repositorio.listarTodos(outroUsuarioId)).thenReturn(Collections.emptyList());

        List<Transacao> resultado = listarTransacao.listarTodos(outroUsuarioId, gerente);

        assertNotNull(resultado);
        verify(repositorio, times(1)).listarTodos(outroUsuarioId);
    }
}
