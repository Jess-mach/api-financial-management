package br.com.ntt.usuario.infra.persistence.entity;

import br.com.ntt.usuario.domain.PerfilUsuario;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "usuarios")
public class UsuarioJpaEntity implements UserDetails {

    @Id
    private UUID id;
    private String nome;
    private String email;
    private String login;
    private String senha;

    @Enumerated(EnumType.STRING)
    private PerfilUsuario perfilUsuario;

    public UsuarioJpaEntity() {}

    public UsuarioJpaEntity(UUID id, String nome, String email, String login, String senha, PerfilUsuario perfilUsuario) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.login = login;
        this.senha = senha;
        this.perfilUsuario = perfilUsuario;

    }

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public PerfilUsuario getPerfilUsuario() {
        return perfilUsuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}

