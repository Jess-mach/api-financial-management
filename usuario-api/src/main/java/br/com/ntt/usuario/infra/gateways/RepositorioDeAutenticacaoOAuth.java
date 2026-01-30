package br.com.ntt.usuario.infra.gateways;

import br.com.ntt.usuario.infra.persistence.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class RepositorioDeAutenticacaoOAuth implements UserDetailsService {

    @Autowired
    private UsuarioRepository repositoryJpa;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UserDetails byLogin = repositoryJpa.findByLogin(login);

        return byLogin;
    }
}
