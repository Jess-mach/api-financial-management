package br.com.ntt.transacao.consumer.infra.gateways;

import br.com.ntt.transacao.consumer.application.gateways.RepositorioSaldoCliente;
import br.com.ntt.transacao.consumer.domain.entity.conta.SaldoConta;
import br.com.ntt.transacao.consumer.domain.entity.transacao.Transacao;
import br.com.ntt.transacao.consumer.domain.model.TipoTransacao;
import br.com.ntt.transacao.consumer.infra.consumer.dto.SaldoContaDto;
import br.com.ntt.transacao.consumer.infra.consumer.mapper.SaldoDtoMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
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

    private final SaldoDtoMapper mapper;

    @Override
    public SaldoConta buscarPorId(Long id) {
        URI uri = URI.create(endpointConsultaSaldo + id);

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .build();
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() != 200)
                throw new IllegalArgumentException("Falha ao consultar saldo");

            SaldoContaDto dto = objectMapper.readValue(response.body(), SaldoContaDto.class);

            return mapper.toDomain(dto);
        } catch (Exception e) {
            log.error("falha na requisição", e.getMessage());
            throw new IllegalArgumentException("Falha ao consultar saldo");
        }
    }

    @Override
    public void atualizarSaldo(SaldoConta saldoConta, Transacao transacao) throws JsonProcessingException {

        URI uri = URI.create(endpointConsultaSaldo + saldoConta.getId());

        BigDecimal valorAtualizado;

        if(transacao.getTipo().equals(TipoTransacao.DEPOSITO))
            valorAtualizado = saldoConta.getSaldo().add(transacao.getValor());
        else
            valorAtualizado = saldoConta.getSaldo().subtract(transacao.getValor());

        saldoConta.setSaldo(valorAtualizado);

        String json = objectMapper.writeValueAsString(saldoConta);

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() != 200)
                throw new IllegalArgumentException("Falha ao consultar saldo");


        } catch (Exception e) {
            log.error("falha na requisição", e.getMessage());
            throw new IllegalArgumentException("Falha ao consultar saldo");
        }

    }
}