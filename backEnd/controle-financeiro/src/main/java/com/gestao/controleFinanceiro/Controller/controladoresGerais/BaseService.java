package com.gestao.controleFinanceiro.Controller.controladoresGerais;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.gestao.controleFinanceiro.Model.usuario.Usuario;
import com.gestao.controleFinanceiro.Repository.usuario.UsuarioRepository;

// Torne a classe abstrata e injete a dependência
@Service
public abstract class BaseService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    protected Usuario getUsuarioLogado() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return usuarioRepository.findByUsuario(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
    }

}