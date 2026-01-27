package br.com.ntt.transacao.producer.infra.controller;

import br.com.ntt.transacao.producer.application.usecases.AnaliseDespesaTransacao;
import br.com.ntt.transacao.producer.application.usecases.BuscarTransacaoPorId;
import br.com.ntt.transacao.producer.application.usecases.CriarTransacao;
import br.com.ntt.transacao.producer.application.usecases.ListarTransacao;
import br.com.ntt.transacao.producer.domain.entities.transacao.AnaliseDeDespesa;
import br.com.ntt.transacao.producer.domain.entities.transacao.Transacao;
import br.com.ntt.transacao.producer.infra.controller.dto.DadosNovaTransacao;
import br.com.ntt.transacao.producer.infra.controller.dto.TransacaoDto;
import br.com.ntt.transacao.producer.infra.controller.mapper.TransacaoDtoMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/transacoes")
@Tag(name = "Animals", description = "API for managing animals in the veterinary clinic")
class TransacaoController {

    private final CriarTransacao criarTransacao;
    private final ListarTransacao listarTransacao;
    private final TransacaoDtoMapper transacaoDtoMapper;
    private final BuscarTransacaoPorId buscarTransacaoPorId;
    private final AnaliseDespesaTransacao analiseDespesaTransacao;


    public TransacaoController(CriarTransacao criarTransacao, ListarTransacao listarTransacao, TransacaoDtoMapper transacaoDtoMapper, BuscarTransacaoPorId buscarTransacaoPorId, AnaliseDespesaTransacao analiseDespesaTransacao) {
        this.criarTransacao = criarTransacao;
        this.listarTransacao = listarTransacao;
        this.transacaoDtoMapper = transacaoDtoMapper;
        this.buscarTransacaoPorId = buscarTransacaoPorId;
        this.analiseDespesaTransacao = analiseDespesaTransacao;
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

    @Operation(
            summary = "Get consultation by ID",
            description = "Retrieves a specific consultation by its ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid data")
    })
    @GetMapping("/analise")
    public ResponseEntity<AnaliseDeDespesa> visualizarGastosDia(@RequestParam("usuarioId") @NotNull UUID usuarioId){
        //Implementar uma funcionalidade que
        //permite aos usuários visualizar um resumo
        //e análise de suas despesas, categorizando
        //as transações, agrupando total gasto no
        //dia, mês.

        AnaliseDeDespesa analiseDeDespesa = analiseDespesaTransacao.visualizarGastos(usuarioId);


        //DEVOLVER UM DTO - CONVERTER OBJETO - TODO

        return ResponseEntity.ok(analiseDeDespesa);
    }
}
