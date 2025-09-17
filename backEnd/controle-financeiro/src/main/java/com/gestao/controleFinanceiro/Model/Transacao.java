package com.gestao.controleFinanceiro.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Transacao {
    private Long id;
    private String descricao;
    private Double valor;
    private String data;
}
