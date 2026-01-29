//package br.com.ntt.transacao.producer.domain.entity;
//
//import br.com.ntt.common.transacao.domain.entity.Transacao;
//import br.com.ntt.transacao.producer.application.gateways.RepositorioDeTransacao;
//import br.com.ntt.transacao.producer.application.usecases.CriarTransacao;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//
//import java.util.UUID;
//
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//public class TransacaoTest {
//
//    private RepositorioDeTransacao repositorioDeTransacao;
//
//    private CriarTransacao criarUsuario;
//
//    @Test
//    @DisplayName("Deve criar transação com sucesso")
//    void deveSalvarTRansaçãoComSucesso() {
//
//        Transacao transacao = new Transacao(
//                UUID.randomUUID(),
//                "Jessica",
//                "jessica@email.com",
//                "jess.login",
//                "Senha@123",
//                PerfilUsuario.USUARIO);
//        when(repositorioDeTransacao.cadastrarTransacao(transacao);
//
//
//        criarUsuario.executar(usuario);
//
//        verify(passwordService).encode("Senha@123");
//        verify(usuarioRepository).save(usuario, "hash_seguro");
//    }
//
//
//}
