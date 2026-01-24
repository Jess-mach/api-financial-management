package br.com.ntt.transacao.producer.infra.controller;

import br.com.ntt.transacao.producer.application.usecases.CriarTransacao;
import br.com.ntt.transacao.producer.application.usecases.ListarTransacao;
import br.com.ntt.transacao.producer.domain.StatusTransacao;
import br.com.ntt.transacao.producer.domain.TipoTransacao;
import br.com.ntt.transacao.producer.domain.entities.transacao.Transacao;
import br.com.ntt.transacao.producer.infra.controller.dto.DadosNovaTransacao;
import br.com.ntt.transacao.producer.infra.controller.dto.TransacaoDto;
import br.com.ntt.transacao.producer.infra.controller.mapper.TransacaoDtoMapper;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    private final CriarTransacao criarTransacao;
    private final ListarTransacao listarTransacao;
    private final TransacaoDtoMapper transacaoDtoMapper;

    public TransacaoController(CriarTransacao criarTransacao, ListarTransacao listarTransacao, TransacaoDtoMapper transacaoDtoMapper) {
        this.criarTransacao = criarTransacao;
        this.listarTransacao = listarTransacao;
        this.transacaoDtoMapper = transacaoDtoMapper;
    }

    @PostMapping
    public TransacaoDto executar(@RequestBody @Valid DadosNovaTransacao dados) {
        Transacao novoTransacao = transacaoDtoMapper.toDomain(dados);
        Transacao salvo = criarTransacao.executar(novoTransacao);

        return transacaoDtoMapper.toDto(salvo);
    }

    @GetMapping
    public List<TransacaoDto> listarUsuarios() {
        return listarTransacao.listarTodos().stream()
                .map(salvo -> transacaoDtoMapper.toDto(salvo))
                .collect(Collectors.toList());
    }
}
