package br.com.ntt.transacao.producer.infra.controller.mapper;


import br.com.ntt.transacao.producer.domain.entities.transacao.analise.AnaliseDeDespesa;
import br.com.ntt.transacao.producer.domain.entities.transacao.analise.RegistroDespesa;
import br.com.ntt.transacao.producer.domain.model.TipoTransacao;
import br.com.ntt.transacao.producer.domain.entities.transacao.transacao.Transacao;
import br.com.ntt.transacao.producer.infra.controller.dto.*;

import java.util.List;


public class TransacaoDtoMapper {

    public Transacao toDomain(DadosNovaTransacaoDto dados) {
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

    public AnaliseDespesaDto toDto(AnaliseDeDespesa dados) {


        List<RegistroDespesaDto> despesadia  = dados.getDia().getDespesas()
                .stream()
                .map(despesaItem -> toItemDto(despesaItem))
                .toList();

        TotalizadorDespesaDto dia = new TotalizadorDespesaDto(despesadia, dados.getDia().getValorTotal());

        List<RegistroDespesaDto> despesames  = dados.getMes().getDespesas()
                .stream()
                .map(despesaItem -> toItemDto(despesaItem))
                .toList();

        TotalizadorDespesaDto mes = new TotalizadorDespesaDto(despesames, dados.getMes().getValorTotal());

        AnaliseDespesaDto dto = new AnaliseDespesaDto(dia,mes);
        return dto;

    }

    private RegistroDespesaDto toItemDto(RegistroDespesa registroDespesa) {
        return new RegistroDespesaDto(
                registroDespesa.getData(),
                registroDespesa.getTipo(),
                registroDespesa.getValor(),
                registroDespesa.getQuantidade());
    }
}
