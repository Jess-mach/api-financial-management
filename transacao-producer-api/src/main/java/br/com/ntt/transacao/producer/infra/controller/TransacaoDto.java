package br.com.ntt.transacao.producer.infra.controller;

import java.time.LocalDate;
import java.util.UUID;

public record TransacaoDto(
        UUID usuarioId,
        String nome,
        LocalDate nascimento,
        String email
) {
}
