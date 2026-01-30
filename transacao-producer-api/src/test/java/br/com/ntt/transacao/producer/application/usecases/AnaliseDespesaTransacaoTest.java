package br.com.ntt.transacao.producer.application.usecases;

import br.com.ntt.common.transacao.domain.entity.AnaliseDeDespesa;
import br.com.ntt.transacao.producer.application.gateways.RepositorioDeTransacao;
import br.com.ntt.transacao.producer.domain.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AnaliseDespesaTransacaoTest {

    @InjectMocks
    private AnaliseDespesaTransacao analiseDespesaTransacao;

    @Mock
    private RepositorioDeTransacao repositorioDeTransacao;

    private UUID usuarioId = UUID.randomUUID();
    private Usuario usuarioLogado = new Usuario(
            usuarioId,
            null,
            "ADMINISTRADOR"
    );

    @Test
    @DisplayName("Deve visualizar gastos com sucesso")
    void devevisualizarGastosComSucesso() {

        when(repositorioDeTransacao.visualizarGastosMes(any())).thenReturn(List.of());

        when(repositorioDeTransacao.visualizarGastosDia(any())).thenReturn(List.of());

        AnaliseDeDespesa analise = analiseDespesaTransacao.visualizarGastos(usuarioId, usuarioLogado);

        assertNotNull(analise);
        assertNotNull(analise.getDia());
        assertNotNull(analise.getDia().getDespesas());
        assertNotNull(analise.getDia().getValorTotal());

        assertNotNull(analise.getMes());
        assertNotNull(analise.getMes().getDespesas());
        assertNotNull(analise.getMes().getValorTotal());

        verify(repositorioDeTransacao, times(1)).visualizarGastosDia(any());
    }


}