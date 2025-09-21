package com.gestao.controleFinanceiro.jwt;

import com.gestao.controleFinanceiro.Model.usuario.Usuario;
import com.gestao.controleFinanceiro.Repository.usuario.UsuarioRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
public class UserDetailsServiceConfig {

    private final UsuarioRepository usuarioRepository;

    public UserDetailsServiceConfig(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Bean
    UserDetailsService userDetailsService() {
        return username -> {
            Usuario usuario = usuarioRepository.findByUsuario(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));

            UserDetails userDetails = User.builder()
                    .username(usuario.getUsuario())
                    .password(usuario.getSenha()) // já está criptografada
                    .roles("USER") // Roles (Tipo de usuário)
                    .build();

            return userDetails;
        };
    }
}
