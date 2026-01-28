package br.com.ntt.usuario.infra.controller;

import br.com.ntt.usuario.application.usecase.*;
import br.com.ntt.usuario.domain.entity.Usuario;
import br.com.ntt.usuario.infra.controller.dto.DadosAtualizacaoUsuario;
import br.com.ntt.usuario.infra.controller.dto.DadosCadastroUsuario;
import br.com.ntt.usuario.infra.controller.dto.UsuarioDto;
import br.com.ntt.usuario.infra.controller.mapper.UsuarioDtoMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

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
        Usuario novoUsuario = usuarioDtoMapper.toDomain(dados);

        novoUsuario = criarUsuario.executar(novoUsuario);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(usuarioDtoMapper.toDto(novoUsuario));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDto>> listar() {
        List<UsuarioDto> lista = listarUsuario.executar()
                .stream()
                .map(salvo -> usuarioDtoMapper.toDto(salvo))
                .toList();

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDto> buscarPorId(@PathVariable UUID id) {
        Usuario usuario  = buscarTransacaoPorId.buscarPorId(id);
        UsuarioDto dto = usuarioDtoMapper.toDto(usuario);

        return ResponseEntity.ok(dto);
    }


    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDto> atualizar(@PathVariable String id, @RequestBody @Valid DadosAtualizacaoUsuario dados) {
        Usuario usuarioAtualizado = usuarioDtoMapper.toDomain(id, dados);
        usuarioAtualizado = atualizarUsuario.executar(usuarioAtualizado);

        return ResponseEntity.ok(usuarioDtoMapper.toDto(usuarioAtualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        deletarUsuario.executar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadArquivo(@RequestParam("file") MultipartFile file) throws Exception {
        List<Usuario> listaUsuarios = arquivoUsuario.processarArquivo(file);

        return ResponseEntity.ok(listaUsuarios);
    }
}
