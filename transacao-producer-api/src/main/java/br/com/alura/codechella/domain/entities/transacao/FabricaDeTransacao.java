package br.com.alura.codechella.domain.entities.transacao;

import br.com.alura.codechella.domain.Endereco;

import java.time.LocalDate;

public class FabricaDeTransacao {
    private Transacao transacao;

    public Transacao comNomeUsuarioIdNascimento(String nome, String usuarioId, LocalDate nascimento){
        this.transacao = new Transacao(usuarioId, nome, nascimento, "");
        return this.transacao;
    }

    public Transacao incluiEndereco(String cep, Integer numero, String complemento) {
        this.transacao.setEndereco(new Endereco(cep, numero, complemento));
        return this.transacao;
    }

}
