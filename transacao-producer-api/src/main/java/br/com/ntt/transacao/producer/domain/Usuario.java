package br.com.ntt.transacao.producer.domain;

import java.util.UUID;

public record Usuario(
        UUID id,
        String nome,
        String perfilUsuario
) {
}
