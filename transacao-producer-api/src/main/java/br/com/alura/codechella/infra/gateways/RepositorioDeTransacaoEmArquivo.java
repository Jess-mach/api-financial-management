package br.com.alura.codechella.infra.gateways;

import br.com.alura.codechella.application.gateways.RepositorioDeTransacao;
import br.com.alura.codechella.domain.entities.transacao.Transacao;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RepositorioDeTransacaoEmArquivo implements RepositorioDeTransacao {
    List<Transacao> transacaos = new ArrayList<>();

    @Override
    public Transacao cadastrarUsuario(Transacao transacao) {
        this.transacaos.add(transacao);
        return transacao;
    }

    @Override
    public List<Transacao> listarTodos() {
        return this.transacaos;
    }

    public void gravaEmArquivo(String nomeArquivo) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(nomeArquivo);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            fileWriter.write(this.transacaos.toString());
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
