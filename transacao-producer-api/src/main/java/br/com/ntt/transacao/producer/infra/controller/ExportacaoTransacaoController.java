package br.com.ntt.transacao.producer.infra.controller;

import br.com.ntt.transacao.producer.application.usecases.ExportarTransacao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/transacoes/exportar")
public class ExportacaoTransacaoController {

    private final ExportarTransacao exportarTransacao;

    public ExportacaoTransacaoController(ExportarTransacao exportarTransacao) {
        this.exportarTransacao = exportarTransacao;
    }

    @GetMapping("/excel")
    public ResponseEntity<Resource> baixarExcel(@RequestParam(value = "usuarioId", required = false) UUID usuarioId) {
        log.info("exportar para excel - inicio");

        ByteArrayInputStream fluxoDados = exportarTransacao.gerarExcel(usuarioId);

        log.info("exportar para excel - fim");

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=relatorio-transacoes.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(new InputStreamResource(fluxoDados));
    }

}
