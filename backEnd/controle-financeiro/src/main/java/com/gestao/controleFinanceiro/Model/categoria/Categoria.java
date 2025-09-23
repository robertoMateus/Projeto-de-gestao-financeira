package com.gestao.controleFinanceiro.Model.categoria;

import java.util.List;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gestao.controleFinanceiro.Model.transacao.Transacao;
import com.gestao.controleFinanceiro.Model.usuario.Usuario;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "A descrição não pode ser vazia")
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonIgnore // Evita serialização em JSON, prevenindo loops de referência
    private Usuario usuario;

    // Relacionamento com Transacao
    // O 'mappedBy' indica o nome do campo na classe Transacao que faz o mapeamento.
    @OneToMany(mappedBy = "categoria")
    @JsonIgnore // Essencial para evitar um loop infinito na serialização JSON
    private List<Transacao> transacoes;
}
