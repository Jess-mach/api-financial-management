package br.com.ntt.transacao.producer.infra.controller;

import br.com.ntt.transacao.producer.application.usecases.BuscarTransacaoPorId;
import br.com.ntt.transacao.producer.application.usecases.CriarTransacao;
import br.com.ntt.transacao.producer.application.usecases.ListarTransacao;
import br.com.ntt.transacao.producer.domain.entities.transacao.AnaliseDeDespesa;
import br.com.ntt.transacao.producer.domain.entities.transacao.Transacao;
import br.com.ntt.transacao.producer.infra.controller.dto.DadosNovaTransacao;
import br.com.ntt.transacao.producer.infra.controller.dto.TransacaoDto;
import br.com.ntt.transacao.producer.infra.controller.mapper.TransacaoDtoMapper;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    private final CriarTransacao criarTransacao;
    private final ListarTransacao listarTransacao;
    private final TransacaoDtoMapper transacaoDtoMapper;
    private final BuscarTransacaoPorId buscarTransacaoPorId;
    private final AnaliseDeDespesa analiseDespesaTRansacao;


    public TransacaoController(CriarTransacao criarTransacao, ListarTransacao listarTransacao, TransacaoDtoMapper transacaoDtoMapper, BuscarTransacaoPorId buscarTransacaoPorId, AnaliseDeDespesa analiseDespesaTRansacao) {
        this.criarTransacao = criarTransacao;
        this.listarTransacao = listarTransacao;
        this.transacaoDtoMapper = transacaoDtoMapper;
        this.buscarTransacaoPorId = buscarTransacaoPorId;
        this.analiseDespesaTRansacao = analiseDespesaTRansacao;
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


    @GetMapping("/{id}")
    public TransacaoDto buscarPorId(@PathVariable UUID id) {
        Transacao transacao  = buscarTransacaoPorId.buscarPorId(id);

        TransacaoDto dto = transacaoDtoMapper.toDto(transacao);

        return dto;
    }

    @GetMapping("/{id}/{gastosDia}")
    public TransacaoDto visualizarGastosDia(@PathVariable UUID usuarioId){
        //Implementar uma funcionalidade que
        //permite aos usuários visualizar um resumo
        //e análise de suas despesas, categorizando
        //as transações, agrupando total gasto no
        //dia, mês.

        AnaliseDeDespesa analiseDeDespesa = analiseDespesaTRansacao.visualizarGastosDia(usuarioId);


        return null;
    }

    //Análise de Despesas: Endpoint para visualização de resumo de gastos,
    // categorizados por dia ou mês.
}
