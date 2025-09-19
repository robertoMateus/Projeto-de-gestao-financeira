package com.gestao.controleFinanceiro.Repository.transacao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gestao.controleFinanceiro.Model.transacao.Transacao;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
    
}
