package com.gestao.controleFinanceiro.Model.usuario;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gestao.controleFinanceiro.Model.transacao.Transacao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Usuário não pode ser vazia")
    @Column(unique = true, nullable = false)
    private String usuario;

    @NotBlank(message = "A senha não pode ser vazia")
    private String senha;

    private String nome;
    
    @NotBlank(message = "O email não pode ser vazio")
    @Email(message = "Email inválido")
    private String email;

    @OneToMany(mappedBy = "usuario")
    @JsonIgnore // Essencial para evitar um loop infinito na serialização JSON
    private List<Transacao> transacoes;
}
