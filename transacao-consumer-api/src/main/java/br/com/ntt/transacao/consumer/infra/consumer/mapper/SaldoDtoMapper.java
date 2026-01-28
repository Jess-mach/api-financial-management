package br.com.ntt.transacao.consumer.infra.consumer.mapper;

import br.com.ntt.transacao.consumer.domain.entity.conta.SaldoConta;
import br.com.ntt.transacao.consumer.infra.consumer.dto.SaldoContaDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SaldoDtoMapper {

    public SaldoConta toDomain(SaldoContaDto dto) {
        return new SaldoConta(
                dto.nome(),
                dto.conta(),
                dto.saldo(),
                dto.id(),
                dto.limite());
    }

    public SaldoContaDto toDto(SaldoConta saldoConta) {
        return new SaldoContaDto(
                LocalDateTime.now().toString(),
                saldoConta.getNome(),
                saldoConta.getConta(),
                saldoConta.getSaldo(),
                saldoConta.getId(),
                saldoConta.getLimiteCartao()
        );
    }
}
