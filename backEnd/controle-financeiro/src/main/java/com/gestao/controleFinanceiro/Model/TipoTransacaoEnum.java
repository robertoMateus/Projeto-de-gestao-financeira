package com.gestao.controleFinanceiro.Model;

public enum TipoTransacaoEnum {
    RECEITA(1),
    DESPESA(2);

     private final int code;
     
     private TipoTransacaoEnum(int code) {
         this.code = code;
     }

     public int getCode() {
         return code;
     }

     public static TipoTransacaoEnum fromCode(int code) {
         for (TipoTransacaoEnum status : TipoTransacaoEnum.values()) {
             if (status.getCode() == code) {
                 return status;
             }
         }
         throw new IllegalArgumentException("Código de status inválido: " + code);
     }


}
