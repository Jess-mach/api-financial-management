package br.com.ntt.transacao.producer.infra.controller;

import br.com.ntt.transacao.producer.application.usecases.ExportarTransacao;
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

@RestController
@RequestMapping("/transacoes/exportar")
public class ExportacaoTransacaoController {

    private final ExportarTransacao exportarTransacao;

    public ExportacaoTransacaoController(ExportarTransacao exportarTransacao) {
        this.exportarTransacao = exportarTransacao;
    }

    @GetMapping("/excel")
    public ResponseEntity<Resource> baixarExcel() throws IOException {
        ByteArrayInputStream fluxoDados = exportarTransacao.gerarExcel();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=relatorio-transacoes.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(new InputStreamResource(fluxoDados));
    }

    //Relatórios: Rota para baixar relatório em PDF ou Excel com o resumo das transações.

}
