package br.com.ntt.transacao.producer.infra.gateways;

import br.com.ntt.transacao.producer.application.gateways.RepositorioConsultaUsuario;
import br.com.ntt.transacao.producer.infra.controller.dto.UsuarioDto;
import br.com.ntt.transacao.producer.infra.controller.mapper.UsuarioDtoMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class RepositorioConsultaUsuarioHttp implements RepositorioConsultaUsuario {

    @Value("${endpoint.consulta.usuario:}")
    private String endpointConsultaSaldo;

    private final HttpClient client = HttpClient.newHttpClient();

    private final ObjectMapper objectMapper;

    private final UsuarioDtoMapper mapper;

    @Override
    public String buscarPorId(UUID id, String token) {
        URI uri = URI.create(endpointConsultaSaldo + id);

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .header("Authorization", token)
                    .build();

            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200)
                throw new ResourceNotFoundException("Usuario não encontrado");

            UsuarioDto dto = objectMapper.readValue(response.body(), UsuarioDto.class);

            return mapper.toDomain(dto);
        } catch (Exception e) {
            log.error("falha na requisição", e.getMessage());
            throw new ResourceNotFoundException("Usuario não encontrado");
        }
    }

}