package br.com.ntt.transacao.producer.infra.controller;

import br.com.ntt.transacao.producer.application.usecases.CriarTransacao;
import br.com.ntt.transacao.producer.application.usecases.ListarTransacao;
import br.com.ntt.transacao.producer.domain.StatusTransacao;
import br.com.ntt.transacao.producer.domain.TipoTransacao;
import br.com.ntt.transacao.producer.domain.entities.transacao.Transacao;
import br.com.ntt.transacao.producer.infra.controller.dto.DadosNovaTransacao;
import br.com.ntt.transacao.producer.infra.controller.dto.TransacaoDto;
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

    public TransacaoController(CriarTransacao criarTransacao, ListarTransacao listarTransacao) {
        this.criarTransacao = criarTransacao;
        this.listarTransacao = listarTransacao;
    }

    @PostMapping
    public TransacaoDto executar(@RequestBody @Valid DadosNovaTransacao dados) {
        Transacao novoTransacao = new Transacao(
                UUID.randomUUID(),
                dados.usuarioId(),
                dados.valor(),
                TipoTransacao.valueOf(dados.tipo()),
                StatusTransacao.PENDENTE,
                LocalDateTime.now(),
                null,
                dados.moeda(),
                null,
                dados.descricao());

        Transacao salvo = criarTransacao.executar(novoTransacao);

        return new TransacaoDto(salvo.getId(), salvo.getUsuarioId(),
                salvo.getValor(), salvo.getTipo(), salvo.getStatus(), salvo.getDataHoraSolicitacao(),
                salvo.getDataHoraFinalizacao(), salvo.getMoeda(), salvo.getTaxaCambio(), salvo.getDescricao());

    }

    @GetMapping
    public List<TransacaoDto> listarUsuarios() {
        return listarTransacao.listarTodos().stream()
                .map(salvo -> new TransacaoDto(salvo.getId(), salvo.getUsuarioId(),
                        salvo.getValor(), salvo.getTipo(), salvo.getStatus(), salvo.getDataHoraSolicitacao(),
                        salvo.getDataHoraFinalizacao(), salvo.getMoeda(), salvo.getTaxaCambio(), salvo.getDescricao()))
                .collect(Collectors.toList());
    }
}
