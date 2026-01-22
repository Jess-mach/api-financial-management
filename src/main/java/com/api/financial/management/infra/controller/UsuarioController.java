package com.api.financial.management.infra.controller;

import com.api.financial.management.application.usecase.AtualizarUsuario;
import com.api.financial.management.application.usecase.CriarUsuario;
import com.api.financial.management.application.usecase.ListarUsuario;
import com.api.financial.management.domain.entity.Usuario;
import com.api.financial.management.infra.controller.dto.DadosAtualizacaoUsuario;
import com.api.financial.management.infra.controller.dto.DadosCadastroUsuario;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final CriarUsuario criarUsuario;
    private final ListarUsuario listarUsuario;
    private final AtualizarUsuario atualizarUsuario;


    public UsuarioController(CriarUsuario criarUsuario, ListarUsuario listarUsuario, AtualizarUsuario atualizarUsuario) {
        this.criarUsuario = criarUsuario;
        this.listarUsuario = listarUsuario;
        this.atualizarUsuario = atualizarUsuario;
    }

    @PostMapping
    public ResponseEntity<Usuario> criar(@RequestBody @Valid DadosCadastroUsuario dados) {
        Usuario novoUsuario = Usuario.criarNovo(
                dados.nome(),
                dados.email(),
                dados.login(),
                dados.senha(),
                dados.perfilUsuario()
        );

        Usuario usuario = criarUsuario.executar(novoUsuario);

        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listar() {
        List<Usuario> usuarios = listarUsuario.executar();
        return ResponseEntity.ok(usuarios);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizar(@PathVariable String id, @RequestBody DadosAtualizacaoUsuario dados) {
        Usuario usuarioAtualizado = Usuario.atualizarUsuario(
                id,
                dados.nome(),
                dados.email(),
                dados.senha(),
                dados.perfilUsuario()

        );
        Usuario usuario = atualizarUsuario.executar(usuarioAtualizado);

        return ResponseEntity.ok(usuario);
    }

}
