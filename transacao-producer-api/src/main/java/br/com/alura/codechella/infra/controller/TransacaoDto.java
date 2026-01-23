package br.com.alura.codechella.infra.controller;

import java.time.LocalDate;
import java.util.UUID;

public record TransacaoDto(
        UUID usuarioId,
        String nome,
        LocalDate nascimento,
        String email
) {
}
