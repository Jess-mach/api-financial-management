package br.com.ntt.usuario.infra.controller;

import br.com.ntt.usuario.application.usecase.*;
import com.api.financial.management.application.usecase.*;
import br.com.ntt.usuario.domain.entity.Usuario;
import br.com.ntt.usuario.infra.controller.dto.DadosAtualizacaoUsuario;
import br.com.ntt.usuario.infra.controller.dto.DadosCadastroUsuario;
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

    public UsuarioController(CriarUsuario criarUsuario, ListarUsuario listarUsuario, AtualizarUsuario atualizarUsuario, DeletarUsuario deletarUsuario, ArquivoUsuario arquivoUsuario) {
        this.criarUsuario = criarUsuario;
        this.listarUsuario = listarUsuario;
        this.atualizarUsuario = atualizarUsuario;
        this.deletarUsuario = deletarUsuario;
        this.arquivoUsuario = arquivoUsuario;
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
    public ResponseEntity<Usuario> atualizar(@PathVariable String id, @RequestBody @Valid DadosAtualizacaoUsuario dados) {
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
