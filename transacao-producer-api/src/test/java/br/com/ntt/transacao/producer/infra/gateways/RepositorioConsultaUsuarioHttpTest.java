package br.com.ntt.transacao.producer.infra.gateways;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

import br.com.ntt.transacao.producer.infra.controller.dto.UsuarioDto;
import br.com.ntt.transacao.producer.infra.controller.mapper.UsuarioDtoMapper;
import br.com.ntt.common.transacao.domain.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import org.slf4j.LoggerFactory;

@ExtendWith(MockitoExtension.class)
class RepositorioConsultaUsuarioHttpTest {

    @InjectMocks
    private RepositorioConsultaUsuarioHttp repositorioConsultaUsuarioHttp;

    @Mock
    private HttpClient mockClient;

    @Mock
    private HttpResponse<String> mockResponse;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private UsuarioDtoMapper mapper;

    @Mock
    private Appender<ILoggingEvent> mockAppender;

    @Captor
    private ArgumentCaptor<ILoggingEvent> logCaptor;

    private final UUID id = UUID.randomUUID();
    private final String token = "Bearer token123";

    @BeforeEach
    void setup() {
        ReflectionTestUtils.setField(repositorioConsultaUsuarioHttp, "client", mockClient);
        ReflectionTestUtils.setField(repositorioConsultaUsuarioHttp, "objectMapper", objectMapper);
        ReflectionTestUtils.setField(repositorioConsultaUsuarioHttp, "endpointConsultaSaldo", "http://api.com/"); // Com as duas barras

        Logger logger = (Logger) LoggerFactory.getLogger(repositorioConsultaUsuarioHttp.getClass());
        logger.addAppender(mockAppender);
    }

//    @Test
//    void deveBuscarPorIdComSucesso() throws Exception {
//        String jsonResponse = "{\"id\":\"123\"}";
//        UsuarioDto dto = new UsuarioDto("UsuarioNome");
//
//        HttpResponse<String> mockResponse = mock(HttpResponse.class);
//
//        when(mockResponse.statusCode()).thenReturn(200);
//        when(mockResponse.body()).thenReturn("""
//                {"nome": "UsuarioNome"}
//                """);
//        when(mockClient.send(any(HttpRequest.class), any())).thenReturn(mockResponse);
//
//        when(mapper.toDomain(dto)).thenReturn("UsuarioNome");
//
//        String result = repositorioConsultaUsuarioHttp.buscarPorId(id, token);
//
//        assertEquals("UsuarioNome", result);
//
//        verify(mockAppender, atLeastOnce()).doAppend(logCaptor.capture());
//
//        boolean encontrouLogSucesso = logCaptor.getAllValues().stream()
//                .anyMatch(event -> event.getFormattedMessage().contains("usuario validado com sucesso"));
//
//        assertTrue(encontrouLogSucesso, "Deveria ter logado o sucesso");
//    }

    @Test
    void deveLancarExcecaoQuandoStatusNaoFor200() throws Exception {
        assertThrows(ResourceNotFoundException.class, () -> repositorioConsultaUsuarioHttp.buscarPorId(id, token));

        verify(mockAppender, atLeastOnce()).doAppend(logCaptor.capture());
        boolean encontrouLogErro = logCaptor.getAllValues().stream()
                .anyMatch(event -> event.getFormattedMessage().contains("falha na requisição"));

        assertTrue(encontrouLogErro);
    }
}