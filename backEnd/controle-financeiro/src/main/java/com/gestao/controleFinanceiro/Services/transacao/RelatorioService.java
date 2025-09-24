package com.gestao.controleFinanceiro.Services.transacao;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gestao.controleFinanceiro.Controller.controladoresGerais.BaseService;
import com.gestao.controleFinanceiro.Dto.CategoriaSomaDto;
import com.gestao.controleFinanceiro.Dto.RelatorioMensalDto;
import com.gestao.controleFinanceiro.Dto.ResumoCategoriaDto;
import com.gestao.controleFinanceiro.Model.transacao.TipoTransacaoEnum;
import com.gestao.controleFinanceiro.Model.usuario.Usuario;
import com.gestao.controleFinanceiro.Repository.transacao.TransacaoRepository;

@Service
public class RelatorioService extends BaseService {
     
    private final TransacaoRepository transacaoRepository;

    public RelatorioService(TransacaoRepository transacaoRepository) {
        this.transacaoRepository = transacaoRepository;
    }

    @Transactional(readOnly = true)
    public RelatorioMensalDto gerarRelatorioMensal(int ano, int mes) {
        LocalDate start = LocalDate.of(ano, mes, 1);
        LocalDate end = start.with(TemporalAdjusters.lastDayOfMonth());
        Usuario usuario = getUsuarioLogado(); // Obtém o usuário logado do BaseService

        // Calcula os saldos antes do mês
        Double receitasBefore = transacaoRepository.sumValorBefore(usuario, start, TipoTransacaoEnum.RECEITA);
        Double despesasBefore = transacaoRepository.sumValorBefore(usuario, start, TipoTransacaoEnum.DESPESA);
        Double saldoInicial = receitasBefore - despesasBefore;

        // Calcula os totais do mês
        Double receitasMes = transacaoRepository.sumValorBetween(usuario, start, end, TipoTransacaoEnum.RECEITA);
        Double despesasMes = transacaoRepository.sumValorBetween(usuario, start, end, TipoTransacaoEnum.DESPESA);
        Double saldoFinal = saldoInicial + (receitasMes - despesasMes);

        // Agrupados por categoria no mês
        List<CategoriaSomaDto> receitasByCat = transacaoRepository.sumByCategoriaBetween(usuario, start, end, TipoTransacaoEnum.RECEITA);
        List<CategoriaSomaDto> despesasByCat = transacaoRepository.sumByCategoriaBetween(usuario, start, end, TipoTransacaoEnum.DESPESA);

        // Agrupados por categoria antes do mês (saldo inicial por categoria)
        List<CategoriaSomaDto> receitasBeforeByCat = transacaoRepository.sumByCategoriaBefore(usuario, start, TipoTransacaoEnum.RECEITA);
        List<CategoriaSomaDto> despesasBeforeByCat = transacaoRepository.sumByCategoriaBefore(usuario, start, TipoTransacaoEnum.DESPESA);

        // Map para juntar os valores por categoria
        Map<Long, ResumoCategoriaDto.Builder> map = new HashMap<>();

        receitasByCat.forEach(c -> {
            Long catId = c.categoriaId();
            String nome = c.categoriaNome();
            map.computeIfAbsent(catId, id -> new ResumoCategoriaDto.Builder(catId, nome))
               .receitasMes(c.total());
        });

        despesasByCat.forEach(c -> {
            Long catId = c.categoriaId();
            String nome = c.categoriaNome();
            map.computeIfAbsent(catId, id -> new ResumoCategoriaDto.Builder(catId, nome))
               .despesasMes(c.total());
        });

        receitasBeforeByCat.forEach(c -> {
            Long catId = c.categoriaId();
            String nome = c.categoriaNome();
            map.computeIfAbsent(catId, id -> new ResumoCategoriaDto.Builder(catId, nome))
               .saldoInicialReceitas(c.total());
        });

        despesasBeforeByCat.forEach(c -> {
            Long catId = c.categoriaId();
            String nome = c.categoriaNome();
            map.computeIfAbsent(catId, id -> new ResumoCategoriaDto.Builder(catId, nome))
               .saldoInicialDespesas(c.total());
        });

        // Converter builders para DTOs finais
        List<ResumoCategoriaDto> porCategoria = new ArrayList<>();
        for (ResumoCategoriaDto.Builder b : map.values()) {
            porCategoria.add(b.build());
        }

        return new RelatorioMensalDto(ano, mes, saldoInicial, receitasMes, despesasMes, saldoFinal, porCategoria);
    }
}
