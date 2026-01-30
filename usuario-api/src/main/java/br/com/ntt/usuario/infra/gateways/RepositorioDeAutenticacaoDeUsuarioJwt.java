package br.com.ntt.usuario.infra.gateways;

import br.com.ntt.usuario.application.gateways.RepositorioDeAutenticacaoDeUsuario;
import br.com.ntt.usuario.domain.entity.Usuario;
import br.com.ntt.usuario.domain.exception.AccessDeniedException;
import br.com.ntt.usuario.infra.persistence.entity.UsuarioJpaEntity;
import br.com.ntt.usuario.infra.persistence.mapper.UsuarioJpaMapper;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Slf4j
@Component
public class RepositorioDeAutenticacaoDeUsuarioJwt implements RepositorioDeAutenticacaoDeUsuario {

    @Value("${api.security.token.secret}")
    private String secret;

    private final AuthenticationManager manager;

    private final UsuarioJpaMapper usuarioJpaMapper;

    public RepositorioDeAutenticacaoDeUsuarioJwt(AuthenticationManager manager, UsuarioJpaMapper usuarioJpaMapper) {
        this.manager = manager;
        this.usuarioJpaMapper = usuarioJpaMapper;
    }

    @Override
    public String gerarToken(String login, String senha) {
        log.info("gerando token de usuario");
        var authenticationToken = new UsernamePasswordAuthenticationToken(login, senha);

        var authentication = manager.authenticate(authenticationToken);

        String token = criaTokenJwt((UsuarioJpaEntity) authentication.getPrincipal());

        log.info("token gerado com sucesso size={}", token.length());

        return token;
    }

    @Override
    public Usuario recuperaUsuarioLogado() {
        UsuarioJpaEntity usuarioJpaEntity = (UsuarioJpaEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return usuarioJpaMapper.toDomain(usuarioJpaEntity);
    }

    private String criaTokenJwt(UsuarioJpaEntity usuario) {
        try {
            var algorithm = Algorithm.HMAC256(secret);

            return JWT.create()
                    .withIssuer("API Financial.management")
                    .withSubject(usuario.getLogin())
                    .withClaim("usuarioId", usuario.getId().toString())
                    .withClaim("role", usuario.getPerfilUsuario().toString())
                    .withIssuedAt(new Date())
                    .withExpiresAt(dataExpiracao())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new AccessDeniedException("Falha ao gerar token jwt", exception);
        }
    }

    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2)
                .toInstant(ZoneOffset.of("-03:00"));
    }

}
