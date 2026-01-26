package br.com.ntt.transacao.producer.infra.gateways;

import br.com.ntt.transacao.producer.application.gateways.RepositorioDeExportacao;
import br.com.ntt.transacao.producer.application.gateways.RepositorioDeTransacao;
import br.com.ntt.transacao.producer.domain.entities.transacao.Transacao;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class RelatorioDeExportacaoTransacaoArquivo implements RepositorioDeExportacao {

    private final DateTimeFormatter formatadorData = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private final RepositorioDeTransacao repositorioDeTransacao;

    public RelatorioDeExportacaoTransacaoArquivo(RepositorioDeTransacao repositorioDeTransacao) {
        this.repositorioDeTransacao = repositorioDeTransacao;
    }

    @Override
    public ByteArrayInputStream gerarExcel() throws IOException {

        List<Transacao> transacoes = repositorioDeTransacao.listarTodos();

        try (Workbook planilha = new XSSFWorkbook();
            ByteArrayOutputStream saida = new ByteArrayOutputStream()) {
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
            for (Transacao t : transacoes) {
                Row linha = aba.createRow(indiceLinha++);

                String dataFinal = (t.getDataHoraFinalizacao() != null)
                        ? t.getDataHoraFinalizacao().format(formatadorData) : "-";
                Double taxaDeCambio = (t.getTaxaCambio() != null)
                        ? t.getTaxaCambio().doubleValue() : 0.0;

                linha.createCell(0).setCellValue(t.getId().toString());
                linha.createCell(1).setCellValue(t.getUsuarioId().toString());
                linha.createCell(2).setCellValue(t.getValor().doubleValue());
                linha.createCell(3).setCellValue(t.getTipo().toString());
                linha.createCell(4).setCellValue(t.getStatus().toString());
                linha.createCell(5).setCellValue(t.getDataHoraSolicitacao().format(formatadorData));
                linha.createCell(6).setCellValue(dataFinal);
                linha.createCell(7).setCellValue(t.getMoeda());
                linha.createCell(8).setCellValue(taxaDeCambio);
                linha.createCell(9).setCellValue(t.getDescricao());
            }

            for (int i = 0; i < colunas.length; i++) {
                aba.autoSizeColumn(i);
            }

            planilha.write(saida);

            return new ByteArrayInputStream(saida.toByteArray());
        }
    }

    @Override
    public ByteArrayInputStream gerarPdf() {return null;}

}
