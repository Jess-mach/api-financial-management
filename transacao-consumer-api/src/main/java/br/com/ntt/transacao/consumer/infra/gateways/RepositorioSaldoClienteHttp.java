package br.com.ntt.transacao.consumer.infra.gateways;

import br.com.ntt.transacao.consumer.application.gateways.RepositorioSaldoCliente;
import br.com.ntt.transacao.consumer.domain.entities.SaldoConta;
import br.com.ntt.transacao.consumer.infra.consumer.dto.SaldoContaDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Slf4j
@Component
@RequiredArgsConstructor
public class RepositorioSaldoClienteHttp implements RepositorioSaldoCliente {

    @Value("${endpoint.consulta.saldo:}")
    private String endpointConsultaSaldo;

    private HttpClient client = HttpClient.newHttpClient();

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public SaldoConta buscarPorId(Long id) {
        URI uri = URI.create(endpointConsultaSaldo + id);

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .build();
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());

            SaldoContaDto dto = objectMapper.readValue(response.body(), SaldoContaDto.class);


            return new SaldoConta(
                    dto.name(),
                    dto.conta(),
                    dto.saldo(),
                    dto.routingNumber(),
                    dto.id()
            );
        } catch (Exception e) {
            log.error("falha na requisição", e.getMessage());
            throw new IllegalArgumentException("Falha ao consultar saldo");
        }
    }
}