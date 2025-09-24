package com.gestao.controleFinanceiro.Repository.transacao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gestao.controleFinanceiro.Dto.CategoriaSomaDto;
import com.gestao.controleFinanceiro.Model.transacao.TipoTransacaoEnum;
import com.gestao.controleFinanceiro.Model.transacao.Transacao;
import com.gestao.controleFinanceiro.Model.usuario.Usuario;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
    
    // Encontra todas as transações para um usuário específico
    List<Transacao> findByUsuario(Usuario usuario);

    // Encontra uma transação por ID e verifica se ela pertence ao usuário
    Optional<Transacao> findByIdAndUsuario(Long id, Usuario usuario);

    // --- Métodos de Soma ---
    
    // Soma os valores das transações para um usuário por tipo (RECEITA ou DESPESA)
    // A sintaxe "t.tipo = :tipo" é a mais correta e flexível.
    @Query("SELECT COALESCE(SUM(t.valor), 0) FROM Transacao t WHERE t.usuario = :usuario AND t.tipo = :tipo")
    Double sumValorByUsuarioAndTipo(@Param("usuario") Usuario usuario, @Param("tipo") TipoTransacaoEnum tipo);
    
    // Unificar a soma de valores antes de uma data para um tipo específico
    // Use o TipoTransacaoEnum como parâmetro em vez de fixar RECEITA/DESPESA
    @Query("SELECT COALESCE(SUM(t.valor), 0) FROM Transacao t " +
           "WHERE t.usuario = :usuario AND t.data < :start AND t.tipo = :tipo")
    Double sumValorBefore(@Param("usuario") Usuario usuario, @Param("start") LocalDate start, @Param("tipo") TipoTransacaoEnum tipo);

    // Unificar a soma de valores entre um período de tempo para um tipo específico
    // Use o TipoTransacaoEnum como parâmetro
    @Query("SELECT COALESCE(SUM(t.valor), 0) FROM Transacao t " +
           "WHERE t.usuario = :usuario AND t.data BETWEEN :start AND :end AND t.tipo = :tipo")
    Double sumValorBetween(@Param("usuario") Usuario usuario, @Param("start") LocalDate start, @Param("end") LocalDate end, @Param("tipo") TipoTransacaoEnum tipo);

    // --- Métodos de Agrupamento por Categoria ---

    // Agrupa e soma transações por categoria dentro de um período, para um tipo específico
    @Query("SELECT new com.gestao.controleFinanceiro.Dto.CategoriaSomaDto(t.categoria.id, t.categoria.descricao, COALESCE(SUM(t.valor), 0)) " +
           "FROM Transacao t " +
           "WHERE t.usuario = :usuario AND t.data BETWEEN :start AND :end AND t.tipo = :tipo " +
           "GROUP BY t.categoria.id, t.categoria.descricao")
    List<CategoriaSomaDto> sumByCategoriaBetween(@Param("usuario") Usuario usuario, @Param("start") LocalDate start, @Param("end") LocalDate end, @Param("tipo") TipoTransacaoEnum tipo);

    // Agrupa e soma transações por categoria antes de uma data, para um tipo específico
    @Query("SELECT new com.gestao.controleFinanceiro.Dto.CategoriaSomaDto(t.categoria.id, t.categoria.descricao, COALESCE(SUM(t.valor), 0)) " +
           "FROM Transacao t " +
           "WHERE t.usuario = :usuario AND t.data < :start AND t.tipo = :tipo " +
           "GROUP BY t.categoria.id, t.categoria.descricao")
    List<CategoriaSomaDto> sumByCategoriaBefore(@Param("usuario") Usuario usuario, @Param("start") LocalDate start, @Param("tipo") TipoTransacaoEnum tipo);
}