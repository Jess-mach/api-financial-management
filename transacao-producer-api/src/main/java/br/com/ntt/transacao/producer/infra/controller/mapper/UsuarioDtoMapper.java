package br.com.ntt.transacao.producer.infra.controller.mapper;

import br.com.ntt.transacao.producer.infra.controller.dto.UsuarioDto;
import org.springframework.stereotype.Component;

@Component
public class UsuarioDtoMapper {
    public String toDomain(UsuarioDto dto) {
        return dto.nome();
    }
}
