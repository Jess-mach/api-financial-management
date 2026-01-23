package br.com.alura.codechella.infra.controller;

import java.time.LocalDate;

public record TransacaoDto(
        String cpf,
        String nome,
        LocalDate nascimento,
        String email
) {
}
