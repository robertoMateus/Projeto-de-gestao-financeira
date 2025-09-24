package com.gestao.controleFinanceiro.Dto;

public record ResumoCategoriaDto( 
    Long categoriaId,
    String categoriaNome,
    Double receitasMes,
    Double despesasMes,
    Double saldoInicialCategoria,
    Double saldoFinalCategoria) {
    public static class Builder {
        private final Long categoriaId;
        private final String categoriaNome;
        private Double receitasMes = 0.0;
        private Double despesasMes = 0.0;
        private Double inicialReceitas = 0.0;
        private Double inicialDespesas = 0.0;

        public Builder(Long categoriaId, String categoriaNome) {
            this.categoriaId = categoriaId;
            this.categoriaNome = categoriaNome;
        }

        public Builder receitasMes(Double v) { this.receitasMes = v == null ? 0.0 : v; return this; }
        public Builder despesasMes(Double v) { this.despesasMes = v == null ? 0.0 : v; return this; }
        public Builder saldoInicialReceitas(Double v) { this.inicialReceitas = v == null ? 0.0 : v; return this; }
        public Builder saldoInicialDespesas(Double v) { this.inicialDespesas = v == null ? 0.0 : v; return this; }

        public ResumoCategoriaDto build() {
            double saldoInicialCategoria = inicialReceitas - inicialDespesas;
            double saldoFinalCategoria = saldoInicialCategoria + (receitasMes - despesasMes);
            return new ResumoCategoriaDto(categoriaId, categoriaNome, receitasMes, despesasMes, saldoInicialCategoria, saldoFinalCategoria);
        }
    }
}
