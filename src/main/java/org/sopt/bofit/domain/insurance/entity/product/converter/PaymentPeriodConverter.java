package org.sopt.bofit.domain.insurance.entity.product.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.sopt.bofit.domain.insurance.entity.product.constant.PaymentPeriod;

@Converter
public class PaymentPeriodConverter implements AttributeConverter<PaymentPeriod, Integer> {

    @Override
    public Integer convertToDatabaseColumn(PaymentPeriod attribute) {
        if (attribute == null) return 0;

        return attribute.getYear();
    }

    @Override
    public PaymentPeriod convertToEntityAttribute(Integer dbData) {
        if (dbData == null) return null;

        return PaymentPeriod.convertToEntityAttribute(dbData);
    }
}
