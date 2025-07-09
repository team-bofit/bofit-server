package org.sopt.bofit.global.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BaseResponse<T> {

    @Schema(example = "200")
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

    public static <T> BaseResponse<T> ok(String message){
        return new BaseResponse<>(null, message);
    }
}
