package org.sopt.bofit.global.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.bofit.global.exception.constant.GlobalErrorCode;
import org.sopt.bofit.global.exception.customexception.InternalException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JsonMapper {
	private final ObjectMapper objectMapper;

    public <T> String toJson(T data) {
        try {
            return objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            log.error("json 직렬화 실패: {}", e.getMessage(), e);
            throw new InternalException(GlobalErrorCode.JSON_SERIALIZATION_ERROR, e.getMessage());
        }
    }

    public <T> T fromJson(Class<T> tClass, String content) {
        try {
            return objectMapper.readValue(content, tClass);
        } catch (JsonProcessingException e) {
            log.error("json 역직렬화 실패: {}", e.getMessage(), e);
            throw new InternalException(GlobalErrorCode.JSON_DESERIALIZATION_ERROR, e.getMessage());
        }
    }
}
