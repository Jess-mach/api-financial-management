package br.com.ntt.transacao.consumer.infra.consumer.mapper;

import br.com.ntt.transacao.consumer.domain.entities.moeda.ConversorMoeda;
import br.com.ntt.transacao.consumer.infra.consumer.dto.ConversorMoedaDto;

public class ConversorMoedaMapper {

    public ConversorMoeda toDomain(ConversorMoedaDto dto){
        return new ConversorMoeda(
                dto.moeda(),
                dto.data(),
                dto.cotacoes());
    }
}
