package com.api.financial.management.infra.controller;

import com.api.financial.management.application.dto.DadosCadastroUsuario;
import com.api.financial.management.application.usecase.CriarUsuario;
import com.api.financial.management.domain.entity.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final CriarUsuario criarUsuario;

    public UsuarioController(CriarUsuario criarUsuario) {
        this.criarUsuario = criarUsuario;
    }

    @PostMapping
    public ResponseEntity<Usuario> criar(@RequestBody DadosCadastroUsuario dados) {

        System.out.println("Dados Recebidos: " + dados);

        Usuario usuario = criarUsuario.executar(dados);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }

}
