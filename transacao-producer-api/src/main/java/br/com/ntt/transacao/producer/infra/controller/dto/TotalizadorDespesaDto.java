package br.com.ntt.transacao.producer.infra.controller.dto;

import java.math.BigDecimal;
import java.util.List;

public record TotalizadorDespesaDto(
        List<RegistroDespesaDto> despesa,
        BigDecimal valorTotal){

}


//despesasPorDia.
//
//stream()
//                .
//
//map(campos ->mapper.
//
//toDomain(campos))
//        .
//
//collect(Collectors.toList());