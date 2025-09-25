package org.sopt.bofit.domain.insurance.entity.product.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.sopt.bofit.domain.insurance.entity.product.constant.MaturityAge;

@Converter
public class MaturityAgeConverter implements AttributeConverter<MaturityAge, Integer> {

    @Override
    public Integer convertToDatabaseColumn(MaturityAge attribute) {
        if (attribute == null) return 0;

        return attribute.getAge();
    }

    @Override
    public MaturityAge convertToEntityAttribute(Integer dbData) {
        if (dbData == null) return null;

        return MaturityAge.toMaturityAge(dbData);
    }
}
