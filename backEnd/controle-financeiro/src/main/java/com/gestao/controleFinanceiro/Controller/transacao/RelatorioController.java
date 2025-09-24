package com.gestao.controleFinanceiro.Controller.transacao;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gestao.controleFinanceiro.Dto.RelatorioMensalDto;
import com.gestao.controleFinanceiro.Services.transacao.RelatorioService;

@RestController
@RequestMapping("/relatorios") // Mudei a URL para ser mais específica
public class RelatorioController {
     
    private final RelatorioService relatorioService;

    public RelatorioController(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
    }

    @GetMapping("/mensal") // URL mais clara
    public ResponseEntity<RelatorioMensalDto> relatorioMensal(
            @RequestParam int ano,
            @RequestParam int mes) {

        RelatorioMensalDto dto = relatorioService.gerarRelatorioMensal(ano, mes);
        return ResponseEntity.ok(dto);
    }
}