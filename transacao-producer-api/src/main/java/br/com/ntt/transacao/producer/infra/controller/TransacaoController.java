package br.com.ntt.transacao.producer.infra.controller;

import br.com.ntt.transacao.producer.application.usecases.AnaliseDespesaTransacao;
import br.com.ntt.transacao.producer.application.usecases.BuscarTransacaoPorId;
import br.com.ntt.transacao.producer.application.usecases.CriarTransacao;
import br.com.ntt.transacao.producer.application.usecases.ListarTransacao;
import br.com.ntt.transacao.producer.domain.entity.AnaliseDeDespesa;
import br.com.ntt.transacao.producer.domain.entity.Transacao;
import br.com.ntt.transacao.producer.infra.controller.dto.AnaliseDespesaDto;
import br.com.ntt.transacao.producer.infra.controller.dto.DadosNovaTransacaoDto;
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
@Tag(name = "Transação", description = "API de Gestão Finacera")
class TransacaoController {

    private final CriarTransacao criarTransacao;
    private final ListarTransacao listarTransacao;
    private final TransacaoDtoMapper transacaoDtoMapper;
    private final BuscarTransacaoPorId buscarTransacaoPorId;
    private final AnaliseDespesaTransacao analiseDespesaTransacao;


    public TransacaoController(CriarTransacao criarTransacao, ListarTransacao listarTransacao,
                               TransacaoDtoMapper transacaoDtoMapper, BuscarTransacaoPorId buscarTransacaoPorId,
                               AnaliseDespesaTransacao analiseDespesaTransacao) {
        this.criarTransacao = criarTransacao;
        this.listarTransacao = listarTransacao;
        this.transacaoDtoMapper = transacaoDtoMapper;
        this.buscarTransacaoPorId = buscarTransacaoPorId;
        this.analiseDespesaTransacao = analiseDespesaTransacao;
    }

    @PostMapping
    public ResponseEntity<TransacaoDto> executar(@RequestBody @Valid DadosNovaTransacaoDto dados) {
        Transacao novoTransacao = transacaoDtoMapper.toDomain(dados);
        novoTransacao = criarTransacao.executar(novoTransacao);

        return ResponseEntity.ok(transacaoDtoMapper.toDto(novoTransacao));
    }

    @GetMapping
    public ResponseEntity<List<TransacaoDto>> listarUsuarios() {
        List<TransacaoDto> lista = listarTransacao.listarTodos().stream()
                .map(salvo -> transacaoDtoMapper.toDto(salvo))
                .collect(Collectors.toList());

        return ResponseEntity.ok(lista);
    }


    @GetMapping("/{id}")
    public ResponseEntity<TransacaoDto> buscarPorId(@PathVariable UUID id) {
        Transacao transacao  = buscarTransacaoPorId.buscarPorId(id);

        TransacaoDto dto = transacaoDtoMapper.toDto(transacao);

        return ResponseEntity.ok(dto);
    }


    @Operation(
            summary = "Analise das Despesas",
            description = "Retornando resumo por dia e Mês"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "400", description = "Data invalida")
    })
    @GetMapping("/analise")
    public ResponseEntity<AnaliseDespesaDto> visualizarGastosDia(@RequestParam("usuarioId") @NotNull UUID usuarioId){
        AnaliseDeDespesa analiseDeDespesa = analiseDespesaTransacao.visualizarGastos(usuarioId);

        AnaliseDespesaDto dto = transacaoDtoMapper.toDto(analiseDeDespesa);

        return ResponseEntity.ok(dto);
    }
}
