package br.com.ntt.transacao.producer.infra.controller;

import br.com.ntt.common.transacao.domain.entity.AnaliseDeDespesa;
import br.com.ntt.common.transacao.domain.entity.Transacao;
import br.com.ntt.common.transacao.infra.controller.dto.TransacaoDto;
import br.com.ntt.common.transacao.infra.controller.mapper.TransacaoDtoMapper;
import br.com.ntt.transacao.producer.application.usecases.AnaliseDespesaTransacao;
import br.com.ntt.transacao.producer.application.usecases.BuscarTransacaoPorId;
import br.com.ntt.transacao.producer.application.usecases.CriarTransacao;
import br.com.ntt.transacao.producer.application.usecases.ListarTransacao;
import br.com.ntt.transacao.producer.domain.Usuario;
import br.com.ntt.transacao.producer.infra.controller.dto.AnaliseDespesaDto;
import br.com.ntt.transacao.producer.infra.controller.dto.DadosNovaTransacaoDto;
import br.com.ntt.transacao.producer.infra.controller.mapper.TransacaoMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/transacoes")
@Tag(name = "Transação", description = "API de Gestão Financeira")
class TransacaoController {

    private final CriarTransacao criarTransacao;
    private final ListarTransacao listarTransacao;
    private final TransacaoMapper transacaoMapper;
    private final TransacaoDtoMapper commonTransacaoMapper;
    private final BuscarTransacaoPorId buscarTransacaoPorId;
    private final AnaliseDespesaTransacao analiseDespesaTransacao;


    public TransacaoController(CriarTransacao criarTransacao, ListarTransacao listarTransacao,
                               TransacaoMapper transacaoMapper, TransacaoDtoMapper commonTransacaoMapper, BuscarTransacaoPorId buscarTransacaoPorId,
                               AnaliseDespesaTransacao analiseDespesaTransacao) {
        this.criarTransacao = criarTransacao;
        this.listarTransacao = listarTransacao;
        this.transacaoMapper = transacaoMapper;
        this.commonTransacaoMapper = commonTransacaoMapper;
        this.buscarTransacaoPorId = buscarTransacaoPorId;
        this.analiseDespesaTransacao = analiseDespesaTransacao;
    }

    @Operation(
            summary = "Criação de Transação",
            description = "Salva e publica uma solicação de transação."
    )
    @PostMapping
    public ResponseEntity<TransacaoDto> executar(@RequestBody @Valid DadosNovaTransacaoDto dados,
                                                 @RequestHeader(value = "Authorization", required = false) String token,
                                                 @AuthenticationPrincipal Usuario usuarioLogado) {

        log.info("criação da transação dados={} - inicio", dados);

        Transacao novoTransacao = transacaoMapper.toDomain(dados, usuarioLogado);

        novoTransacao = criarTransacao.executar(novoTransacao, token);

        TransacaoDto dto = commonTransacaoMapper.toDto(novoTransacao);

        log.info("criação da transação - fim");

        return ResponseEntity.ok(dto);
    }

    @Operation(
            summary = "Listagem de Transação",
            description = "Retorna lista de transações geral e por id do usuário."
    )
    @GetMapping
    public ResponseEntity<List<TransacaoDto>> listarTodos(@RequestParam(value = "usuarioId", required = false) UUID usuarioId,
                                                          @AuthenticationPrincipal Usuario usuarioLogado) {

        log.info("listagem de transações - inicio");

        List<TransacaoDto> lista = listarTransacao.listarTodos(usuarioId, usuarioLogado)
                .stream()
                .map(salvo -> commonTransacaoMapper.toDto(salvo))
                .collect(Collectors.toList());

        log.info("listagem de transações - fim");

        return ResponseEntity.ok(lista);
    }

    @Operation(
            summary = "Buscar transação por id",
            description = "Retorna dados da transação por id."
    )
    @GetMapping("/{id}")
    public ResponseEntity<TransacaoDto> buscarPorId(@PathVariable UUID id,
                                                    @AuthenticationPrincipal Usuario usuarioLogado) {

        log.info("buscar transacao por id - inicio");

        Transacao transacao = buscarTransacaoPorId.buscarPorId(id, usuarioLogado);

        TransacaoDto dto = commonTransacaoMapper.toDto(transacao);

        log.info("buscar transacao por id - fim");

        return ResponseEntity.ok(dto);
    }


    @Operation(
            summary = "Analise das Despesas",
            description = "Retornando resumo por dia e Mês por Usuário"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "400", description = "Data invalida")
    })
    @GetMapping("/analise")
    public ResponseEntity<AnaliseDespesaDto> visualizarGastosDia(@RequestParam("usuarioId") @NotNull UUID usuarioId,
                                                                 @AuthenticationPrincipal Usuario usuarioLogado) {
        log.info("analise de despesas - inicio");

        AnaliseDeDespesa analiseDeDespesa = analiseDespesaTransacao.visualizarGastos(usuarioId, usuarioLogado);

        AnaliseDespesaDto dto = transacaoMapper.toDto(analiseDeDespesa);

        log.info("analise de despesas - fim");

        return ResponseEntity.ok(dto);
    }
}
