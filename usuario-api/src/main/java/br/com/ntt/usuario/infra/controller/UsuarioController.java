package br.com.ntt.usuario.infra.controller;

import br.com.ntt.usuario.application.usecase.*;
import br.com.ntt.usuario.domain.entity.Usuario;
import br.com.ntt.usuario.infra.controller.dto.DadosAtualizacaoUsuario;
import br.com.ntt.usuario.infra.controller.dto.DadosCadastroUsuario;
import br.com.ntt.usuario.infra.controller.dto.UsuarioDto;
import br.com.ntt.usuario.infra.controller.mapper.UsuarioDtoMapper;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final CriarUsuario criarUsuario;
    private final ListarUsuario listarUsuario;
    private final AtualizarUsuario atualizarUsuario;
    private final DeletarUsuario deletarUsuario;
    private final ArquivoUsuario arquivoUsuario;
    private final UsuarioDtoMapper usuarioDtoMapper;
    private final BuscarUsuarioPorId buscarTransacaoPorId;
    private final AuthenticaUsuario authenticaUsuario;

    public UsuarioController(CriarUsuario criarUsuario, ListarUsuario listarUsuario,
                             AtualizarUsuario atualizarUsuario, DeletarUsuario deletarUsuario,
                             ArquivoUsuario arquivoUsuario, UsuarioDtoMapper usuarioDtoMapper, BuscarUsuarioPorId buscarTransacaoPorId, AuthenticaUsuario authenticaUsuario) {
        this.criarUsuario = criarUsuario;
        this.listarUsuario = listarUsuario;
        this.atualizarUsuario = atualizarUsuario;
        this.deletarUsuario = deletarUsuario;
        this.arquivoUsuario = arquivoUsuario;
        this.usuarioDtoMapper = usuarioDtoMapper;
        this.buscarTransacaoPorId = buscarTransacaoPorId;
        this.authenticaUsuario = authenticaUsuario;
    }

    @Operation(
            summary = "Criação de Usuario"
    )
    @PostMapping
    public ResponseEntity<UsuarioDto> criar(@RequestBody @Valid DadosCadastroUsuario dados) {
        log.info("cadastro de usuario - inicio");

        Usuario usuarioLogado = authenticaUsuario.recuperaUsuarioLogado(true);

        Usuario novoUsuario = usuarioDtoMapper.toDomain(dados);

        novoUsuario = criarUsuario.executar(novoUsuario, usuarioLogado);

        UsuarioDto dto = usuarioDtoMapper.toDto(novoUsuario);

        log.info("cadastro de usuario - fim");

        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @Operation(
            summary = "Consulta de todos os usuarios"
    )
    @GetMapping
    public ResponseEntity<List<UsuarioDto>> listar() {
        log.info("listagem de usuarios - inicio");

        Usuario usuarioLogado = authenticaUsuario.recuperaUsuarioLogado(false);

        List<UsuarioDto> lista = listarUsuario.executar(usuarioLogado)
                .stream()
                .map(salvo -> usuarioDtoMapper.toDto(salvo))
                .toList();

        log.info("listagem de usuarios - fim");

        return ResponseEntity.ok(lista);
    }

    @Operation(
            summary = "Consulta de usuarios por ID"
    )
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDto> buscarPorId(@PathVariable UUID id) {
        log.info("consultando usuario {}", id);

        Usuario usuarioLogado = authenticaUsuario.recuperaUsuarioLogado(false);

        Usuario usuario = buscarTransacaoPorId.buscarPorId(id, usuarioLogado);
        UsuarioDto dto = usuarioDtoMapper.toDto(usuario);

        log.info("consultando usuario {}", id);

        return ResponseEntity.ok(dto);
    }

    @Operation(
            summary = "Atualização de usuarios"
    )
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDto> atualizar(@PathVariable String id, @RequestBody @Valid DadosAtualizacaoUsuario dados) {
        log.info("atualização de usuario - inicio");

        Usuario usuarioLogado = authenticaUsuario.recuperaUsuarioLogado(false);

        Usuario usuarioAtualizado = usuarioDtoMapper.toDomain(id, dados);

        usuarioAtualizado = atualizarUsuario.executar(usuarioAtualizado, usuarioLogado);

        UsuarioDto dto = usuarioDtoMapper.toDto(usuarioAtualizado);

        log.info("atualização de usuario - fim");

        return ResponseEntity.ok(dto);
    }

    @Operation(
            summary = "Exclusão de usuarios"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        log.info("exclusão de usuario - inicio");

        Usuario usuarioLogado = authenticaUsuario.recuperaUsuarioLogado(false);

        deletarUsuario.executar(id, usuarioLogado);

        log.info("exclusão de usuario - fim");

        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Upload de usuarios",
            description = "Envio de usuarios por csv ou xlsx"
    )
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<UsuarioDto>> uploadArquivo(@RequestParam("file") MultipartFile file) throws Exception {
        log.info("upload de arquivo - inicio");

        Usuario usuarioLogado = authenticaUsuario.recuperaUsuarioLogado(false);

        List<UsuarioDto> listaUsuarios = arquivoUsuario.processarArquivo(file, usuarioLogado)
                .stream()
                .map(salvo -> usuarioDtoMapper.toDto(salvo))
                .toList();

        log.info("upload de arquivo - fim");

        return ResponseEntity.status(HttpStatus.CREATED).body(listaUsuarios);
    }
}
