package org.sopt.bofit.global.oauth.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.sopt.bofit.global.dto.response.BaseErrorResponse;
import org.sopt.bofit.global.exception.constant.ErrorCode;
import org.sopt.bofit.global.exception.constant.GlobalErrorCode;
import org.sopt.bofit.global.oauth.constant.RequestAttributeConstants;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.sopt.bofit.global.oauth.constant.JwtExceptionConstants.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        Object exceptionType = request.getAttribute(RequestAttributeConstants.EXCEPTION);

        ErrorCode error = getErrorCode(exceptionType);

        response.setStatus(error.getHttpStatus());
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(UTF_8.name());

        String json = new ObjectMapper().writeValueAsString(BaseErrorResponse.of(error));
        response.getWriter().write(json);
    }

    private ErrorCode getErrorCode(Object exceptionType) {
        ErrorCode error;
        if (EXPIRED.equals(exceptionType)) {
            error = GlobalErrorCode.JWT_EXPIRED;
        } else if (INVALID_SIGNATURE.equals(exceptionType)) {
            error = GlobalErrorCode.JWT_INVALID_SIGNATURE;
        } else if (INVALID.equals(exceptionType)) {
            error = GlobalErrorCode.JWT_INVALID;
        } else if(UNSUPPORTED.equals(exceptionType)) {
            error = GlobalErrorCode.JWT_UNSUPPORTED;
        } else {
            error = GlobalErrorCode.UNAUTHORIZED;
        }
        return error;
    }
}
