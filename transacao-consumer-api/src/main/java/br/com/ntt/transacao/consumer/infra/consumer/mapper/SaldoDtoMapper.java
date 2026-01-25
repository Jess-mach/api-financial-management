package br.com.ntt.transacao.consumer.infra.consumer.mapper;

import br.com.ntt.transacao.consumer.domain.entities.SaldoConta;
import br.com.ntt.transacao.consumer.infra.consumer.dto.SaldoContaDto;

public class SaldoDtoMapper {

    public SaldoConta toDomain(SaldoContaDto dto) {
        return new SaldoConta(
                dto.name(),
                dto.conta(),
                dto.saldo(),
                dto.routingNumber(),
                dto.id());
    }

}
