package br.com.ntt.transacao.consumer.infra.gateways;

import br.com.ntt.transacao.consumer.domain.entities.transacao.Transacao;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RepositorioDeTransacaoEmArquivo {
    List<Transacao> transacaos = new ArrayList<>();
    public Transacao cadastrarUsuario(Transacao transacao) {
        this.transacaos.add(transacao);
        return transacao;
    }

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
