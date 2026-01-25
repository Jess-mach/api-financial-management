package br.com.ntt.transacao.consumer.infra.controller;

import br.com.ntt.transacao.consumer.infra.consumer.dto.DadosNovaTransacao;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
        "spring.datasource.url=jdbc:postgresql://localhost:5433/transacoes_db",
        "spring.datasource.username=TEST",
        "spring.datasource.password=TEST",
        "spring.datasource.driver-class-name=org.postgresql.Driver",
        "spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect",
        "hibernate.dialect=org.hibernate.dialect.HSQLDialect"
})
class TransacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

//    @MockBean
//    private PublicadorTransacao publicadorTransacao;

    @Test
    @DisplayName("Deve criar transação com sucesso (Status 202) mesmo sem token real")
    void deveCriarTransacaoComSucesso() throws Exception {

        DadosNovaTransacao request = new DadosNovaTransacao(
                UUID.randomUUID(),
                new BigDecimal("100.50"),
                "DEPOSITO", // Use as Strings exatas do seu Enum
                "Almoço de domingo",
                "BRL"
        );

//        doNothing().when(publicadorTransacao).publicarSolicitacao(any(Transacao.class));

        mockMvc.perform(post("/transacoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))

                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.valor").value(100.50))
                .andExpect(jsonPath("$.status").value("PENDING")); // Confirma que nasceu Pendente
    }

    @Test
    @DisplayName("Deve falhar se valor for negativo (Teste de Validação)")
    void deveFalharComValorNegativo() throws Exception {
        DadosNovaTransacao requestInvalido = new DadosNovaTransacao(
                UUID.randomUUID(),
                new BigDecimal("-50.00"),
                "SAQUE",
                "Erro",
                "BRL"
        );

        mockMvc.perform(post("/transacoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestInvalido)))
                .andExpect(status().isBadRequest());
    }
}