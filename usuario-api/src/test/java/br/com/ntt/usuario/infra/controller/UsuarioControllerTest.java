package br.com.ntt.usuario.infra.controller;


import br.com.ntt.usuario.infra.controller.dto.DadosCadastroUsuario;
import br.com.ntt.usuario.infra.persistence.entity.UsuarioJpaEntity;
import br.com.ntt.usuario.infra.persistence.repository.RepositoryJpa;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import static br.com.ntt.usuario.domain.PerfilUsuario.ADMINISTRADOR;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestPropertySource(properties = {
        "spring.flyway.enabled=false",
        "spring.datasource.url=jdbc:postgresql://localhost:5432/usuarios_db?currentSchema=usuario_test_db",
        "spring.datasource.username=user_usr",
        "spring.datasource.password=password_usr",
        "spring.datasource.driver-class-nome=org.postgresql.Driver",
        "spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect",
        "hibernate.dialect=org.hibernate.dialect.HSQLDialect",
        "spring.jpa.hibernate.ddl-auto=update"
})
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Autowired
    private RepositoryJpa repositoryJpa;

    private UUID usuarioId;

    @Value("classpath:payload/cadastro_massa.csv")
    private Resource sampleFile;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        UsuarioJpaEntity entidade = new UsuarioJpaEntity(
                null,
                "Jessica Machado",
                "teste@test.com",
                "jess.test",
                "senha", ADMINISTRADOR);
        entidade = repositoryJpa.save(entidade);

        usuarioId = entidade.getId();
    }

    private DadosCadastroUsuario gerarNovoUsuario() throws IOException {
        String sobrenome = "Test-" + UUID.randomUUID();

        DadosCadastroUsuario novoUsuario = new DadosCadastroUsuario(
                "Usuario" + sobrenome,
                sobrenome + "@test.com",
                sobrenome,
                "Teste@##1223Teste",
                ADMINISTRADOR

        );

        return novoUsuario;
    }

    @Test
    @DisplayName("GET /usuarios/{id} - Usuario por Id")
    void deveRetornarUsuarioPorIdComSucesso() throws Exception {

        mockMvc.perform(get("/usuarios/" + usuarioId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(usuarioId.toString()))
                .andExpect(jsonPath("$.nome").value("Jessica Machado"))
                .andExpect(jsonPath("$.login").value("jess.test"))
                .andExpect(jsonPath("$.perfilUsuario").value("ADMINISTRADOR"));
    }



    @Test
    @DisplayName("GET /usuarios/{id} - Usuario nao encontrado")
    void deveRetornar404QuandoUsuarioNaoForEncontrado() throws Exception {

        mockMvc.perform(get("/usuarios/" + UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Usuario não encontrado"));


    }

    @Test
    @DisplayName("GET /usuarios/{id} - Usuario invalido")
    void deveRetornar400QuandoIdForInvalido() throws Exception {

        mockMvc.perform(get("/usuarios/0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("GET /usuarios - Listagem de Usuarios")
    void deveRetornarTodosOsUsuarios() throws Exception {

        mockMvc.perform(get("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThan(0))))
                .andExpect(jsonPath("$[0].nome").value(anything()))
                .andExpect(jsonPath("$[0].login").value(anything()))
                .andExpect(jsonPath("$[0].email").value(anything()));

    }

    @Test
    @DisplayName("POST /usuarios - Sucesso ao criar usuario")
    void deveCriarUsuarioComSucesso() throws Exception {

        DadosCadastroUsuario novoUsuario = gerarNovoUsuario();

        mockMvc.perform(post("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content( objectMapper.writeValueAsString(novoUsuario)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nome").value(novoUsuario.nome()))
                .andExpect(jsonPath("$.email").value(novoUsuario.email()))
                .andExpect(jsonPath("$.login").value(novoUsuario.login()))
                .andExpect(jsonPath("$.perfilUsuario").value("ADMINISTRADOR"));
    }

    @Test
    @DisplayName("POST /usuarios/upload - Sucesso ao criar usuario")
    void deveCriarUsuarioViaArquivoComSucesso() throws Exception {

        MockMultipartFile mockFile = new MockMultipartFile(
                "file",
                "cadastro_massa.csv",
                MediaType.APPLICATION_PDF_VALUE,
                sampleFile.getInputStream() // Use the input stream directly
        );

        repositoryJpa.deleteAll();

        mockMvc.perform(multipart("/usuarios/upload")
                        .file(mockFile))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThan(0))));
    }

    @Test
    @DisplayName("POST /usuarios - Erro ao cadastrar usuario com campos vazios")
    void deveREtornarErro404AoCadastrarUsuario() throws Exception {

        String novoUsuario = carregarUsuarioDoArquivo("novo-usuario-erro.json");

        mockMvc.perform(post("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(novoUsuario))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.validationErrors").exists())
                .andExpect(jsonPath("$.validationErrors.senha").exists())
                .andExpect(jsonPath("$.validationErrors.perfilUsuario").value("O perfil é obrigatório"))
                .andExpect(jsonPath("$.validationErrors.nome").exists())
                .andExpect(jsonPath("$.validationErrors.login").exists())
                .andExpect(jsonPath("$.validationErrors.senha").exists());
    }


    @Test
    @DisplayName("PUT /usuarios/{id} - Usuario não encontrado para atualizar")
    void deveRetornar404AoAtualizarUsuarioInexistente() throws Exception {

        String novoUsuario = carregarUsuarioDoArquivo("novo-usuario.json");

        mockMvc.perform(put("/usuarios/" + UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(novoUsuario))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Usuario não encontrado"));

    }

    @Test
    @DisplayName("PUT /usuarios/{id} - Usuario atualizado com Sucesso")
    void deveAtualizarUsuarioAoManterMesmoId() throws Exception {

        DadosCadastroUsuario novoUsuario = gerarNovoUsuario();

        mockMvc.perform(put("/usuarios/" + usuarioId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(novoUsuario)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value(novoUsuario.nome()))
                .andExpect(jsonPath("$.email").value(novoUsuario.email()))
                .andExpect(jsonPath("$.perfilUsuario").value("ADMINISTRADOR"));
    }

    @Test
    @DisplayName("PUT /usuarios/{id} - Usuario invalido")
    void deveRetornar400AoAtualizarUsuarioComIdInvalido() throws Exception {
        String novoUsuarioErro = carregarUsuarioDoArquivo("novo-usuario-erro.json");

        mockMvc.perform(put("/usuarios/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(novoUsuarioErro))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.validationErrors").exists())
                .andExpect(jsonPath("$.message").value("Dados inválidos fornecidos"));

    }

    @Test
    @DisplayName("POST /usuarios - Falha ao criar usuario com campos invalidos")
    void deveRetornar400AoTentarCriarUsario() throws Exception {
        String novoUsuarioErro = carregarUsuarioDoArquivo("novo-usuario-erro.json");

        mockMvc.perform(post("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(novoUsuarioErro))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.validationErrors").exists())
                .andExpect(jsonPath("$.message").value("Dados inválidos fornecidos"));

    }

    @Test
    @DisplayName("DELETE /usuarios/{id} - Usuario Deletado com sucesso")
    void deveDeletarUsuarioComSucesso() throws Exception {

        mockMvc.perform(delete("/usuarios/" + usuarioId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());

    }

    @Test
    @DisplayName("DELETE /usuarios/{id} - Usuario nao encontrado")
    void deveRetornar404AoDeletarUsuarioInexistente() throws Exception {


        mockMvc.perform(delete("/usuarios/" + UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Usuário não encontrado"));

    }

    private String carregarUsuarioDoArquivo(String arquivo) throws IOException {
        return Files.readString(Path.of("src/test/resources/payload/" + arquivo));
    }
}
