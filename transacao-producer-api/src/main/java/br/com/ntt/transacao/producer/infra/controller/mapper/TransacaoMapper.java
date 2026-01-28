package br.com.ntt.transacao.producer.infra.controller.mapper;



import br.com.ntt.common.transacao.domain.entity.AnaliseDeDespesa;
import br.com.ntt.common.transacao.domain.entity.RegistroDespesa;
import br.com.ntt.common.transacao.domain.entity.Transacao;
import br.com.ntt.common.transacao.domain.model.TipoTransacao;
import br.com.ntt.transacao.producer.infra.controller.dto.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TransacaoMapper {

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
