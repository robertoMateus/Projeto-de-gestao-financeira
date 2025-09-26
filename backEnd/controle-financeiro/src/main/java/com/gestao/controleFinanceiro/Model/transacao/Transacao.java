package com.gestao.controleFinanceiro.Model.transacao;

import java.time.LocalDate;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gestao.controleFinanceiro.Model.categoria.Categoria;
import com.gestao.controleFinanceiro.Model.usuario.Usuario;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "A descrição não pode ser vazia")
    private String descricao;

    @NotNull(message = "O valor não pode ser nulo")
    private Double valor;

    @CreatedDate
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "America/Recife")
     private LocalDate data;

    @NotNull(message = "O tipo de transação não pode ser nulo")
    private TipoTransacaoEnum tipo;

    
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonIgnore // Evita serialização em JSON, prevenindo loops de referência
    private Usuario usuario;

    @NotNull(message = "A categoria não pode ser nula")
    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;
}