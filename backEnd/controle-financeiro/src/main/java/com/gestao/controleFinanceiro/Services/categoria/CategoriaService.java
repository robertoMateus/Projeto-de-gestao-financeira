package com.gestao.controleFinanceiro.Services.categoria;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestao.controleFinanceiro.Controller.controladoresGerais.BaseService;
import com.gestao.controleFinanceiro.Model.categoria.Categoria;
import com.gestao.controleFinanceiro.Repository.categoria.CategoriaRepository;

import jakarta.validation.Valid;


@Service
public class CategoriaService extends BaseService{
    

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> get() {
        return categoriaRepository.findByUsuario(getUsuarioLogado());
    }

    public Optional<Categoria> getById(Long id) {
       // Valida se a transação pertence ao usuário logado antes de retornar
        return categoriaRepository.findByIdAndUsuario(id, getUsuarioLogado());
    }

    public Categoria post(@Valid Categoria categoria) {
        // Associa a transação ao usuário logado antes de salvar
        categoria.setUsuario(getUsuarioLogado());
        return categoriaRepository.save(categoria);
    }

    public Categoria put(Long id, Categoria categoria) {
        // Valida se a transação a ser atualizada pertence ao usuário logado
        Optional<Categoria> categoriaExistente = categoriaRepository.findByIdAndUsuario(id, getUsuarioLogado());
        if (categoriaExistente.isPresent()) {
            categoria.setId(id);
            categoria.setUsuario(getUsuarioLogado());
            return categoriaRepository.save(categoria);
        }
        return null; // ou lançar uma exceção
    }

    public void delete(Long id) {
        // Valida se a transação a ser deletada pertence ao usuário logado
        Optional<Categoria> categoriaExistente = categoriaRepository.findByIdAndUsuario(id, getUsuarioLogado());
        if (categoriaExistente.isPresent()) {
            categoriaRepository.deleteById(id);
        }
    }
}
