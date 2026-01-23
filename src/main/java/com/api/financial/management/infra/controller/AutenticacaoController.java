package com.api.financial.management.infra.controller;

import com.api.financial.management.infra.controller.dto.DadosAutenticacao;
import com.api.financial.management.infra.service.DadosToken;
import com.api.financial.management.infra.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<DadosToken> efetuarLogin(@RequestBody @Valid DadosAutenticacao dados) {
        var tokenJWT = tokenService.gerarToken(dados.login(), dados.senha());

        return ResponseEntity.ok(new DadosToken(tokenJWT));
    }

}
