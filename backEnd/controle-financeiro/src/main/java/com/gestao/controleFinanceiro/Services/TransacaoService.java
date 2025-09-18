package com.gestao.controleFinanceiro.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestao.controleFinanceiro.Model.Transacao;
import com.gestao.controleFinanceiro.Repository.TransacaoRepository;

@Service
public class TransacaoService {
    
    @Autowired
    private TransacaoRepository transacaoRepository;

    public List<Transacao>get() {
        return transacaoRepository.findAll();
    }

    public Optional<Transacao> getById(Long id) {
        return transacaoRepository.findById(id);
    }

      public Transacao post(Transacao transacao) {
        return transacaoRepository.save(transacao);
    }

    public Transacao put(Long id, Transacao transacao) {
        if (transacaoRepository.existsById(id)) {
            transacao.setId(id);
            return transacaoRepository.save(transacao);
        }
        return null;
    }

     public void delete(Long id) {
        transacaoRepository.deleteById(id);
    }
}
