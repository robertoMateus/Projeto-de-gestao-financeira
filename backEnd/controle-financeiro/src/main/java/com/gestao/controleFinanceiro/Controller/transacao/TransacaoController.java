package com.gestao.controleFinanceiro.Controller.transacao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestao.controleFinanceiro.Model.transacao.Transacao;
import com.gestao.controleFinanceiro.Services.transacao.TransacaoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {
    
    @Autowired
    private TransacaoService transacaoService;
    
   @GetMapping
    public ResponseEntity<List<Transacao>> getTransacao() {
        return ResponseEntity.ok(transacaoService.get());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transacao> getTransacaoById(@PathVariable Long id) {
        return transacaoService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

     @PostMapping
    public ResponseEntity<Transacao> postTransacao(@Valid @RequestBody Transacao transacao) {
        return ResponseEntity.ok(transacaoService.post(transacao));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transacao> putTransacao(@PathVariable Long id, @RequestBody Transacao transacao) {
        Transacao transacaoAtualizada = transacaoService.put(id, transacao);
        if (transacaoAtualizada != null) {
            return ResponseEntity.ok(transacaoAtualizada);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransacao(@PathVariable Long id) {
        transacaoService.delete(id);
        return ResponseEntity.noContent().build();
    }

     @GetMapping("/saldo")
    public ResponseEntity<Double> getSaldo() {
        Double saldo = transacaoService.calcularSaldo();
        return ResponseEntity.ok(saldo);
    }
}
