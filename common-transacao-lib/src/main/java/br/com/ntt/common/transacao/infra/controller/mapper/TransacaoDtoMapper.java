package br.com.ntt.common.transacao.infra.controller.mapper;


import br.com.ntt.common.transacao.domain.entity.Transacao;
import br.com.ntt.common.transacao.infra.controller.dto.TransacaoDto;
import org.springframework.stereotype.Component;

@Component
public class TransacaoDtoMapper {

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
                salvo.getDescricao(),
                salvo.getConta());
    }

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
                dto.descricao(),
                dto.conta());
    }
}
