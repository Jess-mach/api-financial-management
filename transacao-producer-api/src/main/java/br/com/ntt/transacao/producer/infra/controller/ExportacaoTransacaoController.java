package br.com.ntt.transacao.producer.infra.controller;

import br.com.ntt.transacao.producer.application.usecases.ListarTransacao;
import br.com.ntt.transacao.producer.domain.entities.transacao.Transacao;
import br.com.ntt.transacao.producer.infra.controller.dto.TransacaoDto;
import br.com.ntt.transacao.producer.infra.controller.mapper.TransacaoDtoMapper;
import br.com.ntt.transacao.producer.infra.service.RelatorioTransacaoService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/transacoes/exportar")
public class ExportacaoTransacaoController {

    private final RelatorioTransacaoService relatorioService;
    private final ListarTransacao listarTransacao;
    private final TransacaoDtoMapper mapper;

    public ExportacaoTransacaoController(RelatorioTransacaoService relatorioService, ListarTransacao listarTransacao,
                                         TransacaoDtoMapper mapper) {
        this.relatorioService = relatorioService;
        this.listarTransacao = listarTransacao;
        this.mapper = mapper;
    }

    @GetMapping("/excel")
    public ResponseEntity<Resource> baixarExcel() throws IOException {
        ByteArrayInputStream fluxoDados = relatorioService.gerarExcel();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=relatorio-transacoes.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(new InputStreamResource(fluxoDados));
    }

}
