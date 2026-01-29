package br.com.ntt.transacao.producer.application.gateways;

import java.util.UUID;

public interface RepositorioConsultaUsuario {
    String buscarPorId(UUID id, String token);
}
