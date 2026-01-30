package br.com.ntt.transacao.producer.infra.controller;

import br.com.ntt.transacao.producer.application.gateways.RepositorioConsultaUsuario;
import br.com.ntt.transacao.producer.infra.controller.dto.DadosNovaTransacaoDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestPropertySource(properties = {
        "spring.flyway.enabled=false",
        "spring.datasource.url=jdbc:postgresql://localhost:5433/transacoes_db?currentSchema=transacoes_test_db",
        "spring.datasource.username=db_user",
        "spring.datasource.password=db_password",
        "spring.datasource.driver-class-name=org.postgresql.Driver",
        "spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect",
        "hibernate.dialect=org.hibernate.dialect.HSQLDialect"
})
class TransacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RepositorioConsultaUsuario repositorioConsultaUsuario;

    @Test
    @DisplayName("Deve criar transação com sucesso (Status 202) mesmo sem token real")
    void deveCriarTransacaoComSucesso() throws Exception {

        DadosNovaTransacaoDto request = new DadosNovaTransacaoDto(
                UUID.randomUUID(),
                new BigDecimal("100.50"),
                "DEPOSITO",
                "Almoço de domingo",
                "BRL",
                1L
        );

        mockMvc.perform(post("/transacoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.valor").value(100.50))
                .andExpect(jsonPath("$.status").value("PENDENTE"));
    }

    @Test
    @DisplayName("Deve falhar se valor for negativo (Teste de Validação)")
    void deveFalharComValorNegativo() throws Exception {
        DadosNovaTransacaoDto requestInvalido = new DadosNovaTransacaoDto(
                UUID.randomUUID(),
                new BigDecimal("-50.00"),
                "SAQUE",
                "Erro",
                "BRL",
                1L
        );

        mockMvc.perform(post("/transacoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestInvalido)))
                .andExpect(status().isBadRequest());
    }
}