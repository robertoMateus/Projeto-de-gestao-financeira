package com.gestao.controleFinanceiro.Services.transacao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.gestao.controleFinanceiro.Model.transacao.TipoTransacaoEnum;
import com.gestao.controleFinanceiro.Model.transacao.Transacao;
import com.gestao.controleFinanceiro.Model.usuario.Usuario;
import com.gestao.controleFinanceiro.Repository.transacao.TransacaoRepository;
import com.gestao.controleFinanceiro.Repository.usuario.UsuarioRepository;

import jakarta.validation.Valid;

@Service
public class TransacaoService {

    @Autowired
    private UsuarioRepository usuarioRepository; // Injeta o repositório de usuário

    @Autowired
    private TransacaoRepository transacaoRepository;
    
    // Método para obter o usuário logado
    private Usuario getUsuarioLogado() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return usuarioRepository.findByUsuario(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
    }
    public List<Transacao> get() {
         return transacaoRepository.findByUsuario(getUsuarioLogado());
    }

    public Optional<Transacao> getById(Long id) {
       // Valida se a transação pertence ao usuário logado antes de retornar
        return transacaoRepository.findByIdAndUsuario(id, getUsuarioLogado());
    }

     public Transacao post(@Valid Transacao transacao) {
        // Associa a transação ao usuário logado antes de salvar
        transacao.setUsuario(getUsuarioLogado());
        return transacaoRepository.save(transacao);
    }

    public Transacao put(Long id, Transacao transacao) {
        // Valida se a transação a ser atualizada pertence ao usuário logado
        Optional<Transacao> transacaoExistente = transacaoRepository.findByIdAndUsuario(id, getUsuarioLogado());
        if (transacaoExistente.isPresent()) {
            transacao.setId(id);
            transacao.setUsuario(getUsuarioLogado());
            return transacaoRepository.save(transacao);
        }
        return null; // ou lançar uma exceção
    }

    public void delete(Long id) {
        // Valida se a transação a ser deletada pertence ao usuário logado
        Optional<Transacao> transacaoExistente = transacaoRepository.findByIdAndUsuario(id, getUsuarioLogado());
        if (transacaoExistente.isPresent()) {
            transacaoRepository.deleteById(id);
        }
    }

    // NOVO MÉTODO: Calcula o saldo do usuário logado
    public Double calcularSaldo() {
        Usuario usuario = getUsuarioLogado();

        Double entradas = transacaoRepository.sumValorByUsuarioAndTipo(usuario, TipoTransacaoEnum.RECEITA);
        Double saidas = transacaoRepository.sumValorByUsuarioAndTipo(usuario, TipoTransacaoEnum.DESPESA);

        // Retorna o saldo, garantindo que valores nulos sejam tratados como 0
        return (entradas != null ? entradas : 0.0) - (saidas != null ? saidas : 0.0);
    }
}
