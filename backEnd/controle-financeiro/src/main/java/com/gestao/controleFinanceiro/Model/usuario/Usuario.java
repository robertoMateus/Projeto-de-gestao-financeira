package com.gestao.controleFinanceiro.Model.usuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    @Column(unique = true, nullable = false)
    private String usuario;

    @NotBlank(message = "A senha não pode ser vazia")
    private String senha;

    private String nome;
    
    @NotBlank(message = "O email não pode ser vazio")
    @Email(message = "Email inválido")
    private String email;
}
