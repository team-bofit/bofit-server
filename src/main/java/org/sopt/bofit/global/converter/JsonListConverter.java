package org.sopt.bofit.global.converter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.sopt.bofit.global.exception.constant.GlobalErrorCode;
import org.sopt.bofit.global.exception.customexception.InternalException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public abstract class JsonListConverter<T> implements AttributeConverter<List<T>, String> {
	private static final ObjectMapper mapper = new ObjectMapper();

	private final Class<T> clazz;

	private static final String EMPTY_LIST = "[]";

	protected JsonListConverter(Class<T> clazz) {
		this.clazz = clazz;
	}

	@Override
	public String convertToDatabaseColumn(List<T> attribute) {
		if (attribute == null || attribute.isEmpty()) return EMPTY_LIST;
		try {
			return mapper.writeValueAsString(attribute);
		} catch (JsonProcessingException e) {
			throw new InternalException(GlobalErrorCode.JSON_SERIALIZATION_ERROR);
		}
	}

	@Override
	public List<T> convertToEntityAttribute(String dbData) {
		if(dbData == null || dbData.trim().isEmpty()) return new ArrayList<>();
		try {
			return mapper.readValue(dbData,
				mapper.getTypeFactory().constructCollectionType(List.class, clazz));
		} catch (IOException e) {
			throw new InternalException(GlobalErrorCode.JSON_DESERIALIZATION_ERROR);
		}
	}
}
