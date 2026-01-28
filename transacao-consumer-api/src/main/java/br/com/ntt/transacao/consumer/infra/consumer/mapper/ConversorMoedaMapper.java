package br.com.ntt.transacao.consumer.infra.consumer.mapper;

import br.com.ntt.transacao.consumer.domain.entities.cotacao.Cotacao;
import br.com.ntt.transacao.consumer.domain.entities.moeda.ConversorMoeda;
import br.com.ntt.transacao.consumer.infra.consumer.dto.ConversorMoedaDto;
import br.com.ntt.transacao.consumer.infra.consumer.dto.CotacoesDto;
import org.springframework.stereotype.Component;

@Component
public class ConversorMoedaMapper {

    public ConversorMoeda toDomain(ConversorMoedaDto dto){
        return new ConversorMoeda(
                dto.moeda(),
                dto.data(),
                dto.cotacoes()
                        .stream()
                        .map(this::toDomain)
                        .toList()
        );
    }

    public Cotacao toDomain(CotacoesDto dto){
        return new Cotacao(
                dto.paridade_compra(),
                dto.paridade_venda(),
                dto.cotacao_compra(),
                dto.cotacao_venda(),
                dto.data_hora_cotacao(),
                dto.tipo_boletim()
                );
    }

}
