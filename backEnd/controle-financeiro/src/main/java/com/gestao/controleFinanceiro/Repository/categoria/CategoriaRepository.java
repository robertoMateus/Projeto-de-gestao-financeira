package com.gestao.controleFinanceiro.Repository.categoria;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gestao.controleFinanceiro.Model.categoria.Categoria;
import com.gestao.controleFinanceiro.Model.usuario.Usuario;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    List<Categoria> findByUsuario(Usuario usuario);
    // Encontra uma transação por ID e verifica se ela pertence ao usuário
    Optional<Categoria> findByIdAndUsuario(Long id, Usuario usuario);
    
} 
