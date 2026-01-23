package br.com.alura.codechella.infra.controller;

import br.com.alura.codechella.application.usecases.CriarTransacao;
import br.com.alura.codechella.application.usecases.ListarTransacao;
import br.com.alura.codechella.domain.entities.transacao.Transacao;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuarios")
public class TransacaoController {

    private final CriarTransacao criarTransacao;
    private final ListarTransacao listarTransacao;

    public TransacaoController(CriarTransacao criarTransacao, ListarTransacao listarTransacao) {
        this.criarTransacao = criarTransacao;
        this.listarTransacao = listarTransacao;
    }

    @PostMapping
    public TransacaoDto cadastrarUsuario(@RequestBody TransacaoDto dto) {
        Transacao novoTransacao = new Transacao(dto.cpf(),
                dto.nome(),
                dto.nascimento(),
                dto.email());

        Transacao salvo = criarTransacao.cadastrarUsuario(novoTransacao);

        return new TransacaoDto(salvo.getCpf(), salvo.getNome(), salvo.getNascimento(), salvo.getEmail());

    }

    @GetMapping
    public List<TransacaoDto> listarUsuarios() {
        return listarTransacao.obterTodosUsuario().stream()
                .map(u -> new TransacaoDto(u.getCpf(), u.getNome(), u.getNascimento(), u.getEmail()))
                .collect(Collectors.toList());
    }
}
