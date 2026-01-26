package br.com.ntt.usuario.application.usecase;

import br.com.ntt.usuario.domain.PerfilUsuario;
import br.com.ntt.usuario.domain.entity.Usuario;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ArquivoUsuario {

    private final CriarUsuario criarUsuario;

    public ArquivoUsuario(CriarUsuario criarUsuario) {
        this.criarUsuario = criarUsuario;
    }

    public List<Usuario> processarArquivo(MultipartFile file) throws Exception {
        String filename = file.getOriginalFilename();
        List<Usuario> usuarios;

        if (filename != null && filename.endsWith(".csv")) {
            usuarios = lerCsv(file);
        } else if (filename != null && (filename.endsWith(".xls") || filename.endsWith(".xlsx"))) {
            usuarios = lerExcel(file);
        } else {
            throw new IllegalArgumentException("Formato de arquivo não suportado. Use CSV ou Excel.");
        }

        List<Usuario> lote = criarUsuario.lote(usuarios);

        return lote;
    }

    private List<Usuario> lerCsv(MultipartFile file) throws Exception {
        List<Usuario> usuarios = new ArrayList<>();

        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {

            for (CSVRecord csvRecord : csvParser) {
                Usuario usuario = Usuario.criarNovo(
                        csvRecord.get("nome"),
                        csvRecord.get("email"),
                        csvRecord.get("login"),
                        csvRecord.get("senha"),
                        PerfilUsuario.valueOf(csvRecord.get("perfilUsuario"))
                );

                usuarios.add(usuario);
            }
        }
        return usuarios;
    }

    private List<Usuario> lerExcel(MultipartFile file) throws Exception {
        List<Usuario> usuarios = new ArrayList<>();
        DataFormatter dataFormatter = new DataFormatter();

        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;

                //0=nome, 1=email, 2=login, 3=senha, 4=perfil
                String perfil = dataFormatter.formatCellValue(row.getCell(4));

                if(perfil == null || perfil.isBlank())
                    break;

                Usuario usuario = Usuario.criarNovo(
                        dataFormatter.formatCellValue(row.getCell(0)),
                        dataFormatter.formatCellValue(row.getCell(1)),
                        dataFormatter.formatCellValue(row.getCell(2)),
                        dataFormatter.formatCellValue(row.getCell(3)),
                        PerfilUsuario.valueOf(perfil)
                );

                usuarios.add(usuario);
            }
        }
        return usuarios;
    }
}