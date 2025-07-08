package org.sopt.bofit.global.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.sopt.bofit.domain.user.entity.constant.CoveragePreference;
import org.sopt.bofit.global.exception.constant.GlobalErrorCode;
import org.sopt.bofit.global.exception.custom_exception.InternalException;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@Converter
public class CoveragePreferenceMapConverter implements AttributeConverter<Map<CoveragePreference,Integer>,String> {
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Map<CoveragePreference, Integer> attribute) {
        try {
            return mapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new InternalException(GlobalErrorCode.JSON_SERIALIZATION_ERROR);
        }
    }

    @Override
    public Map<CoveragePreference, Integer> convertToEntityAttribute(String dbData) {
        try {
            return mapper.readValue(dbData, new TypeReference<LinkedHashMap<CoveragePreference, Integer>>() {});
        } catch (IOException e) {
            throw new InternalException(GlobalErrorCode.JSON_DESERIALIZATION_ERROR);
        }
    }
}
