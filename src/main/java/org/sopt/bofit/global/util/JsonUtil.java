package org.sopt.bofit.global.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.sopt.bofit.global.exception.constant.GlobalErrorCode;
import org.sopt.bofit.global.exception.customexception.InternalException;

public class JsonUtil {
	private static final ObjectMapper objectMapper = new ObjectMapper();

	public static <T> T parseClass(Class<T> tClass, String content) {
		try {
			return objectMapper.readValue(content, tClass);
		} catch (JsonProcessingException e) {
			throw new InternalException(GlobalErrorCode.JSON_SERIALIZATION_ERROR, e.getMessage());
		}
	}
}
