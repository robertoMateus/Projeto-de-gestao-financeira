package com.gestao.controleFinanceiro.Dto;

import java.util.List;

public record RelatorioMensalDto(
        int ano,
        int mes,
        Double saldoInicial,
        Double receitasMes,
        Double despesasMes,
        Double saldoFinal,
        List<ResumoCategoriaDto> porCategoria) {

}
