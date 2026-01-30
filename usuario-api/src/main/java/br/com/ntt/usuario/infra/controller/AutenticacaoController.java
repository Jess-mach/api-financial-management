package br.com.ntt.usuario.infra.controller;

import br.com.ntt.usuario.application.usecase.AuthenticaUsuario;
import br.com.ntt.usuario.domain.entity.DadosToken;
import br.com.ntt.usuario.infra.controller.dto.DadosAutenticacao;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    private final AuthenticaUsuario authenticaUsuario;

    public AutenticacaoController(AuthenticaUsuario authenticaUsuario) {
        this.authenticaUsuario = authenticaUsuario;
    }

    @Operation(
            summary = "Efetuar login na API e gerar o Token para Autenticação"
    )
    @PostMapping
    public ResponseEntity<DadosToken> efetuarLogin(@RequestBody @Valid DadosAutenticacao dados) {
        log.info("efetuando login - inicio");

        String tokenJWT = authenticaUsuario.gerarToken(dados.login(), dados.senha());

        DadosToken body = new DadosToken(tokenJWT);

        log.info("efetuando login - fim");

        return ResponseEntity.ok(body);
    }

}
