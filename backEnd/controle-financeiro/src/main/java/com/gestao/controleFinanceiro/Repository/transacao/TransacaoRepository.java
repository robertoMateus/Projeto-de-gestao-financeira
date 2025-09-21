package com.gestao.controleFinanceiro.Repository.transacao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gestao.controleFinanceiro.Model.transacao.TipoTransacaoEnum;
import com.gestao.controleFinanceiro.Model.transacao.Transacao;
import com.gestao.controleFinanceiro.Model.usuario.Usuario;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
    // Encontra todas as transações para um usuário específico
    List<Transacao> findByUsuario(Usuario usuario);

    // Encontra uma transação por ID e verifica se ela pertence ao usuário
    Optional<Transacao> findByIdAndUsuario(Long id, Usuario usuario);
    
    // Soma os valores das transações de um usuário por tipo (RECEITA ou DESPESA)
    @Query("SELECT SUM(t.valor) FROM Transacao t WHERE t.usuario = :usuario AND t.tipo = :tipo")
    Double sumValorByUsuarioAndTipo(Usuario usuario, TipoTransacaoEnum tipo);
}
