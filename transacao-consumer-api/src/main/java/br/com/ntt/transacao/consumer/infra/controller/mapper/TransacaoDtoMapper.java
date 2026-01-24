package br.com.ntt.transacao.consumer.infra.controller.mapper;


import br.com.ntt.transacao.consumer.domain.TipoTransacao;
import br.com.ntt.transacao.consumer.domain.entities.transacao.Transacao;
import br.com.ntt.transacao.consumer.infra.controller.dto.DadosNovaTransacao;
import br.com.ntt.transacao.consumer.infra.controller.dto.TransacaoDto;


public class TransacaoDtoMapper {

    public Transacao toDomain(DadosNovaTransacao dados) {
        return new Transacao(
                null,
                dados.usuarioId(),
                dados.valor(),
                TipoTransacao.valueOf(dados.tipo()),
                null,
                null,
                null,
                dados.moeda(),
                null,
                dados.descricao());
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
