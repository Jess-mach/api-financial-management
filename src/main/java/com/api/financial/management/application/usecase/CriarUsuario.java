package com.api.financial.management.application.usecase;

import com.api.financial.management.application.dto.DadosCadastroUsuario;
import com.api.financial.management.domain.entity.DadosAutenticacao;
import com.api.financial.management.domain.entity.Usuario;
import com.api.financial.management.domain.repository.UsuarioRepository;

public class CriarUsuario {

    private final UsuarioRepository usuarioRepository;


    public CriarUsuario(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario executar(DadosCadastroUsuario dados) {
        if (usuarioRepository.existsByEmail(dados.email())) {
            throw new IllegalArgumentException("Já existe um usuário com este e-mail.");
        }
        DadosAutenticacao autenticacao = new  DadosAutenticacao(dados.login(),dados.senha());
        Usuario novoUsuario = Usuario.criarNovo(
                dados.nome(),
                dados.email(),
                autenticacao,
                dados.perfilUsuario()
        );
        return usuarioRepository.save(novoUsuario);
    }
}
