package br.com.ntt.transacao.consumer.infra.gateways;

import br.com.ntt.transacao.consumer.application.gateways.RepositorioContaCliente;
import br.com.ntt.transacao.consumer.domain.entities.transacao.Transacao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Slf4j
public class RepositorioContaClienteHttp implements RepositorioContaCliente {

    @Value(value = "${endpoint.consulta.saldo}")
    private String endpointConsultaSaldo;

    @Override
    public Transacao buscarPorId(Long id) {
        URI uri = URI.create(endpointConsultaSaldo + id);

        HttpClient client = HttpClient.newHttpClient();
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .build();
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
        } catch (Exception e) {
            log.error("falha na requisição", e.getMessage());
            throw new IllegalArgumentException("Falha ao consultar saldo");
        }

        return null;
    }
}
