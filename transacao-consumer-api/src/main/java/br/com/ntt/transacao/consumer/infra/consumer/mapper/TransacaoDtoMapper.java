package br.com.ntt.transacao.consumer.infra.consumer.mapper;


import br.com.ntt.transacao.consumer.domain.entities.transacao.Transacao;
import br.com.ntt.transacao.consumer.infra.consumer.dto.TransacaoDto;


public class TransacaoDtoMapper {

    public Transacao toDomain(TransacaoDto dto) {
        return new Transacao(
                dto.id(),
                dto.usuarioId(),
                dto.valor(),
                dto.tipo(),
                dto.status(),
                dto.dataHoraSolicitacao(),
                dto.dataHoraFinalizacao(),
                dto.moeda(),
                dto.taxaCambio(),
                dto.descricao());
    }

    public TransacaoDto toDto(Transacao salvo) {
        return new TransacaoDto(
                salvo.getId(),
                salvo.getUsuarioId(),
                salvo.getValor(),
                salvo.getTipo(),
                salvo.getStatus(),
                salvo.getDataHoraSolicitacao(),
                salvo.getDataHoraFinalizacao(),
                salvo.getMoeda(),
                salvo.getTaxaCambio(),
                salvo.getDescricao());
    }
}
