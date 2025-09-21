package com.gestao.controleFinanceiro.Services;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gestao.controleFinanceiro.Exception.ControladorException;
import com.gestao.controleFinanceiro.Model.usuario.Usuario;
import com.gestao.controleFinanceiro.Repository.usuario.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario criarUsuario(Usuario usuario) {
        if (usuarioRepository.findByUsuario(usuario.getUsuario()).isPresent()) {
            throw new ControladorException("Usuário já existe");
        }

        if (usuario.getSenha().length() < 6) {
            throw new ControladorException("Senha deve ter pelo menos 6 caracteres");
        }

        // Criptografa a senha antes de salvar
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> buscarPorUsername(String username) {
        return usuarioRepository.findByUsuario(username);
    }

    public boolean validarSenha(String senhaNaoCriptografada, String senhaCriptografada) {
        return passwordEncoder.matches(senhaNaoCriptografada, senhaCriptografada);
    }
}
