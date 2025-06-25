package org.sopt.bofit.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.sopt.bofit.global.exception.constant.ErrorCode;

@Getter
@AllArgsConstructor
public class BaseErrorResponse<T> {
    private final int code;
    private final String message;
    private final String messageDetail;

    public static <T> BaseErrorResponse<T> of(int code, String message, String messageDetail) {
        return new BaseErrorResponse<>(code, message, messageDetail);
    }

    public static <T> BaseErrorResponse<T> of(ErrorCode errorCode){
        return new BaseErrorResponse<>(errorCode.getHttpStatus(),errorCode.getMessage(), null);
    }

}
