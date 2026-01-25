package br.com.ntt.transacao.consumer.infra.consumer.mapper;

import br.com.ntt.transacao.consumer.domain.entities.cotacao.Cotacao;
import br.com.ntt.transacao.consumer.infra.consumer.dto.CotacoesDto;

public class CotacaoDtoMapper {

    public Cotacao toDomain(CotacoesDto dto){
        return new Cotacao(
                dto.paridade_compra(),
                dto.paridade_venda(),
                dto.cotacao_compra(),
                dto.cotacao_venda(),
                dto.data_hora_cotacao(),
                dto.tipo_boletim());
    }

}
