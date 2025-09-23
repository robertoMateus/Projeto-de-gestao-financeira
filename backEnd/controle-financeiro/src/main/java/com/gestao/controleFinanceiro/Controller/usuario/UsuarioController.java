package com.gestao.controleFinanceiro.Controller.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestao.controleFinanceiro.Model.usuario.Usuario;
import com.gestao.controleFinanceiro.Services.usuario.UsuarioService;
import com.gestao.controleFinanceiro.jwt.JwtUtil;

import jakarta.validation.Valid;

import java.util.Map;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@Valid @RequestBody Usuario usuario) {
        Usuario novoUsuario = usuarioService.criarUsuario(usuario);
        return ResponseEntity.ok(novoUsuario);
    }

  @PostMapping("/login")
public ResponseEntity<?> login(@RequestBody Usuario usuario) {
    return usuarioService.buscarPorUsername(usuario.getUsuario())
            .map(u -> {
                // Valida a senha enviada pelo usuário
                if (usuarioService.validarSenha(usuario.getSenha(), u.getSenha())) {
                    // Gera token JWT
                    String token = jwtUtil.gerarToken(u.getUsuario());
                    // Retorna token, usuário e tempo de expiração em JSON
                    return ResponseEntity.ok(Map.of(
                            "token", token,
                            "usuario", u.getUsuario(),
                            "expiracao", System.currentTimeMillis() + 1000L * 60 * 60 * 10
                    ));
                } else {
                    // Mensagem genérica para não vazar se o usuário existe
                    return ResponseEntity.status(401).body("Usuário ou senha inválidos");
                }
            })
            .orElse(ResponseEntity.status(401).body("Usuário ou senha inválidos"));
}

}
