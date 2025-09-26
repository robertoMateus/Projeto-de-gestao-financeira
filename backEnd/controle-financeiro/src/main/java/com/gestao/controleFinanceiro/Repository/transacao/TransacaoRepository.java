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
    
    List<Transacao> findByUsuario(Usuario usuario);

    Optional<Transacao> findByIdAndUsuario(Long id, Usuario usuario);

    @Query("SELECT COALESCE(SUM(t.valor), 0) FROM Transacao t WHERE t.usuario = :usuario AND t.tipo = :tipo")
    Double sumValorByUsuarioAndTipo(@Param("usuario") Usuario usuario, @Param("tipo") TipoTransacaoEnum tipo);
    
    @Query("SELECT COALESCE(SUM(t.valor), 0) FROM Transacao t " +
           "WHERE t.usuario = :usuario AND t.data < :start AND t.tipo = :tipo")
    Double sumValorBefore(@Param("usuario") Usuario usuario, @Param("start") LocalDate start, @Param("tipo") TipoTransacaoEnum tipo);

    @Query("SELECT COALESCE(SUM(t.valor), 0) FROM Transacao t " +
           "WHERE t.usuario = :usuario AND t.data BETWEEN :start AND :end AND t.tipo = :tipo")
    Double sumValorBetween(@Param("usuario") Usuario usuario, @Param("start") LocalDate start, @Param("end") LocalDate end, @Param("tipo") TipoTransacaoEnum tipo);

    @Query("SELECT new com.gestao.controleFinanceiro.Dto.CategoriaSomaDto(t.categoria.id, t.categoria.descricao, COALESCE(SUM(t.valor), 0)) " +
           "FROM Transacao t " +
           "WHERE t.usuario = :usuario AND t.data BETWEEN :start AND :end AND t.tipo = :tipo " +
           "GROUP BY t.categoria.id, t.categoria.descricao")
    List<CategoriaSomaDto> sumByCategoriaBetween(@Param("usuario") Usuario usuario, @Param("start") LocalDate start, @Param("end") LocalDate end, @Param("tipo") TipoTransacaoEnum tipo);

    @Query("SELECT new com.gestao.controleFinanceiro.Dto.CategoriaSomaDto(t.categoria.id, t.categoria.descricao, COALESCE(SUM(t.valor), 0)) " +
           "FROM Transacao t " +
           "WHERE t.usuario = :usuario AND t.data < :start AND t.tipo = :tipo " +
           "GROUP BY t.categoria.id, t.categoria.descricao")
    List<CategoriaSomaDto> sumByCategoriaBefore(@Param("usuario") Usuario usuario, @Param("start") LocalDate start, @Param("tipo") TipoTransacaoEnum tipo);
}