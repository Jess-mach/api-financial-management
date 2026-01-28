package br.com.ntt.common.transacao.infra.gateways;

import br.com.ntt.common.transacao.domain.entity.RegistroDespesa;
import br.com.ntt.common.transacao.domain.model.StatusTransacao;
import br.com.ntt.common.transacao.domain.entity.Transacao;
import br.com.ntt.common.transacao.domain.model.TipoTransacao;
import br.com.ntt.common.transacao.infra.persistence.AnaliseDeDespesaCampos;
import br.com.ntt.common.transacao.infra.persistence.TransacaoEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class TransacaoEntityMapper {

    public TransacaoEntity toEntity(Transacao dados){
        return new TransacaoEntity(
                UUID.randomUUID(),
                dados.getUsuarioId(),
                dados.getValor(),
                dados.getTipo(),
                StatusTransacao.PENDENTE,
                LocalDateTime.now(),
                null,
                dados.getMoeda(),
                null,
                dados.getDescricao());
    }

    public Transacao toDomain(TransacaoEntity entity){
        return new Transacao(
                entity.getId(),
                entity.getUsuarioId(),
                entity.getValor(),
                entity.getTipo(),
                entity.getStatus(),
                entity.getDataHoraSolicitacao(),
                entity.getDataHoraFinalizacao(),
                entity.getMoeda(),
                entity.getTaxaCambio(),
                entity.getDescricao());
    }

    public RegistroDespesa toDomain(AnaliseDeDespesaCampos campos) {
        return new RegistroDespesa(
                campos.getDataHoraSolicitacao(),
                TipoTransacao.getDoCodigo(campos.getTipo()),
                campos.getValor(),
                campos.getQuantidade()
        );
    }
}
