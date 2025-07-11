package org.sopt.bofit.global.oauth.util;

import java.util.List;

import org.sopt.bofit.global.exception.constant.GlobalErrorCode;
import org.sopt.bofit.global.exception.custom_exception.InternalException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
