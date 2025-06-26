package org.sopt.bofit.global.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BaseResponse<T> {
    private final int code;
    private final T data;

    private BaseResponse(T data) {
        this.code = HttpStatus.OK.value();
        this.data = data;
    }

    public static <T> BaseResponse<T> ok(T data) {
        return new BaseResponse<>(data);
    }
}
