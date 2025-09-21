package com.gestao.controleFinanceiro.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    private final Key CHAVE_SECRETA;

    // Injeta a chave secreta do ambiente (desenvolvimento .env ou variável de produção)
    public JwtUtil(@Value("${JWT_SECRET_KEY}") String secret) {
        if (secret == null || secret.getBytes().length < 32) {
            throw new IllegalArgumentException("JWT_SECRET_KEY deve ter pelo menos 32 bytes para HS256.");
        }
        this.CHAVE_SECRETA = Keys.hmacShaKeyFor(secret.getBytes());
    }

    // Gera token JWT para o username informado
    public String gerarToken(String username) {
        long tempoExpiracao = 1000L * 60 * 60 * 10; // 10 horas
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + tempoExpiracao))
                .signWith(CHAVE_SECRETA)
                .compact();
    }

    // Extrai o username de um token válido
    public String extrairUsername(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(CHAVE_SECRETA)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Token expirado.", e);
        } catch (JwtException e) {
            throw new RuntimeException("Token inválido.", e);
        }
    }

    // Verifica se o token é válido e pertence ao username informado
    public boolean validarToken(String token, String username) {
        try {
            String tokenUsername = extrairUsername(token);
            return tokenUsername.equals(username) && !isTokenExpirado(token);
        } catch (Exception e) {
            return false;
        }
    }

    // Verifica se o token expirou
    private boolean isTokenExpirado(String token) {
        try {
            Date expiracao = Jwts.parserBuilder()
                    .setSigningKey(CHAVE_SECRETA)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration();
            return expiracao.before(new Date());
        } catch (JwtException e) {
            return true;
        }
    }
}
