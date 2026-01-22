//package com.api.financial.management.domain.entity;
//
//public class ValidadorUsuario {
//    public Usuario validar(Usuario usuarioEntidade, Usuario usuarioAtualizacao) {
//        if (usuarioAtualizacao.getNome() != null && !usuarioAtualizacao.getNome().isBlank()) {
//            usuarioEntidade.atualizarNome(usuarioAtualizacao.getNome());
//        }
//
//        if (usuarioAtualizacao.getEmail() != null && !usuarioAtualizacao.getEmail().isBlank() && usuarioAtualizacao.getEmail().contains("@")) {
//            usuarioEntidade.atualizarEmail(usuarioAtualizacao.getEmail());
//        }
//
//        if(usuarioAtualizacao.getSenha() != null && !usuarioAtualizacao.getSenha().isBlank() && usuarioAtualizacao.getSenha().length() < 8){
//            throw new IllegalArgumentException("A senha deve conter pelo menos 8 caracteres, uma letra maiúscula," +
//                    "\"uma letra minúscula, um número e um caractere especial");
//        } else
//            usuarioEntidade.atualizarSenha(usuarioAtualizacao.getSenha());
//
//        return usuarioEntidade;
//    }
//}
