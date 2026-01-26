package br.com.ntt.transacao.producer.infra.service;

import br.com.ntt.transacao.producer.infra.controller.dto.TransacaoDto;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class RelatorioTransacaoService {

    private final DateTimeFormatter formatadorData = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public ByteArrayInputStream gerarExcel(List<TransacaoDto> transacoes) throws IOException {
        try (Workbook planilha = new XSSFWorkbook(); ByteArrayOutputStream saida = new ByteArrayOutputStream()) {
            Sheet aba = planilha.createSheet("Transações");

            Row linhaCabecalho = aba.createRow(0);
            String[] colunas = {
                    "ID Transação", "ID Usuário", "Valor", "Tipo", "Status",
                    "Solicitado em", "Finalizado em", "Moeda", "Taxa de Câmbio", "Descrição"
            };

            for (int i = 0; i < colunas.length; i++) {
                Cell celula = linhaCabecalho.createCell(i);
                celula.setCellValue(colunas[i]);

                CellStyle estilo = planilha.createCellStyle();
                Font fonte = planilha.createFont();
                fonte.setBold(true);
                estilo.setFont(fonte);
                celula.setCellStyle(estilo);
            }

            int indiceLinha = 1;
            for (TransacaoDto t : transacoes) {
                Row linha = aba.createRow(indiceLinha++);

                linha.createCell(0).setCellValue(t.id().toString());
                linha.createCell(1).setCellValue(t.usuarioId().toString());
                linha.createCell(2).setCellValue(t.valor().doubleValue());
                linha.createCell(3).setCellValue(t.tipo().toString());
                linha.createCell(4).setCellValue(t.status().toString());
                linha.createCell(5).setCellValue(t.dataHoraSolicitacao().format(formatadorData));
                String dataFinal = (t.dataHoraFinalizacao() != null) ? t.dataHoraFinalizacao().format(formatadorData) : "-";
                linha.createCell(6).setCellValue(dataFinal);
                linha.createCell(7).setCellValue(t.moeda());
                linha.createCell(8).setCellValue(t.taxaCambio().doubleValue());
                linha.createCell(9).setCellValue(t.descricao());
            }

            for (int i = 0; i < colunas.length; i++) {
                aba.autoSizeColumn(i);
            }

            planilha.write(saida);
            return new ByteArrayInputStream(saida.toByteArray());
        }
    }
}
