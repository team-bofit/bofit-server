package org.sopt.bofit.global.converter;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import org.sopt.bofit.global.exception.constant.GlobalErrorCode;
import org.sopt.bofit.global.exception.custom_exception.InternalException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class ListJsonConverter implements AttributeConverter<List<Object>, String> {
	private static final ObjectMapper mapper = new ObjectMapper();
	@Override
	public String convertToDatabaseColumn(List<Object> attribute) {
		try {
			return mapper.writeValueAsString(attribute);
		} catch (JsonProcessingException e) {
			throw new InternalException(GlobalErrorCode.JSON_SERIALIZATION_ERROR);
		}
	}

	@Override
	public List<Object> convertToEntityAttribute(String dbData) {
		try {
			return mapper.readValue(dbData, new TypeReference<>() {});
		} catch (IOException e) {
			throw new InternalException(GlobalErrorCode.JSON_DESERIALIZATION_ERROR);
		}
	}
}
