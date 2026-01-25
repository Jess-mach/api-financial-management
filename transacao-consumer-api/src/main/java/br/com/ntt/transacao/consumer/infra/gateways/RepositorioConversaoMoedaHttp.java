package br.com.ntt.transacao.consumer.infra.gateways;

import br.com.ntt.transacao.consumer.application.gateways.RepositorioConversaoMoeda;
import br.com.ntt.transacao.consumer.domain.entities.moeda.ConversorMoeda;
import br.com.ntt.transacao.consumer.infra.consumer.dto.ConversorMoedaDto;
import br.com.ntt.transacao.consumer.infra.consumer.mapper.CotacaoDtoMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class RepositorioConversaoMoedaHttp implements RepositorioConversaoMoeda {

    @Value("${endpoint.conversor.moeda:}")
    private String endpointConversorMoeda;

    private HttpClient client = HttpClient.newHttpClient();

    private ObjectMapper objectMapper = new ObjectMapper();

    private CotacaoDtoMapper mapper;


    @Override
    public ConversorMoeda conversaoMoeda(String moeda, LocalDateTime dataHoraSolicitacao) {

        if (moeda.equals("BRL"))
            return new ConversorMoeda(moeda, dataHoraSolicitacao.toLocalDate().toString());

        String str = endpointConversorMoeda + moeda + "/" + ajustarParaUltimoDiaUtil(dataHoraSolicitacao.toLocalDate());
        URI uri = URI.create(str);

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .build();
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() != 200)
                throw new IllegalArgumentException("Falha ao converter moeda");

            ConversorMoedaDto dto = objectMapper.readValue(response.body(), ConversorMoedaDto.class);

            return mapper.toDomain(dto);

        } catch (Exception e) {
            log.error("falha na requisição", e.getMessage());
            throw new IllegalArgumentException("Falha ao converter moeda");
        }
    }

    public static LocalDate ajustarParaUltimoDiaUtil(LocalDate data) {
        DayOfWeek dia = data.getDayOfWeek();

        if (dia == DayOfWeek.SATURDAY) {
            return data.minusDays(1);
        } else if (dia == DayOfWeek.SUNDAY) {
            return data.minusDays(2);
        }

        return data;
    }
}
