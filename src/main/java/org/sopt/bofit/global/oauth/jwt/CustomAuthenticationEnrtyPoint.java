package org.sopt.bofit.global.oauth.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.sopt.bofit.global.exception.constant.ErrorCode;
import org.sopt.bofit.global.exception.constant.GlobalErrorCode;
import org.sopt.bofit.global.exception.constant.OAuthErrorCode;
import org.sopt.bofit.global.response.BaseErrorResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEnrtyPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        Object exceptionType = request.getAttribute("exception");

        ErrorCode error = getErrorCode(exceptionType);

        response.setStatus(error.getHttpStatus());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String json = new ObjectMapper().writeValueAsString(BaseErrorResponse.of(error));
        response.getWriter().write(json);
    }

    private ErrorCode getErrorCode(Object exceptionType) {
        ErrorCode error;
        if ("JWT_EXPIRED".equals(exceptionType)) {
            error = GlobalErrorCode.JWT_EXPIRED;
        } else if ("JWT_INVALID_SIGNATURE".equals(exceptionType)) {
            error = GlobalErrorCode.JWT_INVALID_SIGNATURE;
        } else if ("JWT_INVALID".equals(exceptionType)) {
            error = GlobalErrorCode.JWT_INVALID;
        } else if("JWT_UNSUPPORTED".equals(exceptionType)) {
            error = GlobalErrorCode.JWT_UNSUPPORTED;
        } else {
            error = GlobalErrorCode.UNAUTHORIZED;
        }
        return error;
    }
}
