package org.sopt.bofit.global.config;

import lombok.RequiredArgsConstructor;
import org.sopt.bofit.global.oauth.jwt.LoginArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final LoginArgumentResolver loginArgumentResolver;

    private static final String frondEndOrigin = "https://bofit.co.kr";

    private static final String localAddress = "localhost";

    private static final String frontEndPort = "5173";

    private static final String httpPrefix = "http://";

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginArgumentResolver);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedOrigins(frondEndOrigin,
                        httpPrefix + localAddress + ":" + frontEndPort);
    }
}

