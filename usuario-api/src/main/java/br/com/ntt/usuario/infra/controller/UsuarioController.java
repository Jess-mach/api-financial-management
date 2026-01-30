package br.com.ntt.usuario.infra.controller;

import br.com.ntt.usuario.application.usecase.*;
import br.com.ntt.usuario.domain.entity.Usuario;
import br.com.ntt.usuario.infra.controller.dto.DadosAtualizacaoUsuario;
import br.com.ntt.usuario.infra.controller.dto.DadosCadastroUsuario;
import br.com.ntt.usuario.infra.controller.dto.UsuarioDto;
import br.com.ntt.usuario.infra.controller.mapper.UsuarioDtoMapper;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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


    public UsuarioController(CriarUsuario criarUsuario, ListarUsuario listarUsuario,
                             AtualizarUsuario atualizarUsuario, DeletarUsuario deletarUsuario,
                             ArquivoUsuario arquivoUsuario, UsuarioDtoMapper usuarioDtoMapper, BuscarUsuarioPorId buscarTransacaoPorId) {
        this.criarUsuario = criarUsuario;
        this.listarUsuario = listarUsuario;
        this.atualizarUsuario = atualizarUsuario;
        this.deletarUsuario = deletarUsuario;
        this.arquivoUsuario = arquivoUsuario;
        this.usuarioDtoMapper = usuarioDtoMapper;
        this.buscarTransacaoPorId = buscarTransacaoPorId;
    }

    @PostMapping
    public ResponseEntity<UsuarioDto> criar(@RequestBody @Valid DadosCadastroUsuario dados) {
        log.info("cadastro de usuario - inicio");

        Usuario novoUsuario = usuarioDtoMapper.toDomain(dados);

        novoUsuario = criarUsuario.executar(novoUsuario);

        UsuarioDto dto = usuarioDtoMapper.toDto(novoUsuario);

        log.info("cadastro de usuario - fim");

        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDto>> listar() {
        log.info("listagem de usuarios - inicio");

        List<UsuarioDto> lista = listarUsuario.executar()
                .stream()
                .map(salvo -> usuarioDtoMapper.toDto(salvo))
                .toList();

        log.info("listagem de usuarios - fim");

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDto> buscarPorId(@PathVariable UUID id) {
        log.info("consultando usuario {}", id);

        Usuario usuario  = buscarTransacaoPorId.buscarPorId(id);
        UsuarioDto dto = usuarioDtoMapper.toDto(usuario);

        log.info("consultando usuario {}", id);

        return ResponseEntity.ok(dto);
    }


    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDto> atualizar(@PathVariable String id, @RequestBody @Valid DadosAtualizacaoUsuario dados) {
        log.info("atualização de usuario - inicio");

        Usuario usuarioAtualizado = usuarioDtoMapper.toDomain(id, dados);

        usuarioAtualizado = atualizarUsuario.executar(usuarioAtualizado);

        UsuarioDto dto = usuarioDtoMapper.toDto(usuarioAtualizado);

        log.info("atualização de usuario - fim");

        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        log.info("exclusão de usuario - inicio");

        deletarUsuario.executar(id);

        log.info("exclusão de usuario - fim");

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadArquivo(@RequestParam("file") MultipartFile file) throws Exception {
        log.info("upload de arquivo - inicio");

        List<Usuario> listaUsuarios = arquivoUsuario.processarArquivo(file);

        log.info("upload de arquivo - fim");

        return ResponseEntity.status(HttpStatus.CREATED).body(listaUsuarios);
    }
}
