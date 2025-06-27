package org.sopt.bofit.global.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BaseResponse<T> {
    private final int code;
    private final String message;
    private final T data;

    private BaseResponse(T data, String message) {
        this.code = HttpStatus.OK.value();
        this.message = message;
        this.data = data;
    }

    public static <T> BaseResponse<T> ok(T data, String message) {
        return new BaseResponse<>(data, message);
    }
}
