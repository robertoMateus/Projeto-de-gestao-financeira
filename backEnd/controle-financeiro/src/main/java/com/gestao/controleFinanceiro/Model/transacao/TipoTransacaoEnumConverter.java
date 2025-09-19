package com.gestao.controleFinanceiro.Model.transacao;

import jakarta.persistence.AttributeConverter;

public class TipoTransacaoEnumConverter implements AttributeConverter<TipoTransacaoEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TipoTransacaoEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCode();
    }

    @Override
    public TipoTransacaoEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return TipoTransacaoEnum.fromCode(dbData);
    }
    
}
