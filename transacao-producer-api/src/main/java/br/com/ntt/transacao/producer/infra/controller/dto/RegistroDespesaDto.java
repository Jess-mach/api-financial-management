package br.com.ntt.transacao.producer.infra.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record RegistroDespesaDto(
        LocalDate data,
        String tipo,
        BigDecimal valor,
        Long quantidade){
}