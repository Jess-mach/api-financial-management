package com.api.financial.management.infra.controller;

import com.api.financial.management.application.dto.DadosCadastroUsuario;
import com.api.financial.management.application.usecase.CriarUsuario;
import com.api.financial.management.application.usecase.ListarUsuario;
import com.api.financial.management.domain.entity.Usuario;
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

    public UsuarioController(CriarUsuario criarUsuario, ListarUsuario listarUsuario) {
        this.criarUsuario = criarUsuario;
        this.listarUsuario = listarUsuario;
    }

    @PostMapping
    public ResponseEntity<Usuario> criar(@RequestBody @Valid DadosCadastroUsuario dados) {
        Usuario usuario = criarUsuario.executar(dados);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listar() {
        List<Usuario> usuarios = listarUsuario.executar();
        return ResponseEntity.ok(usuarios);
    }


}
