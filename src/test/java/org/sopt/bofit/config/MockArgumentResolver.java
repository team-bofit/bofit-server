package org.sopt.bofit.config;

import org.sopt.bofit.global.annotation.LoginUserId;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


public class MockArgumentResolver implements HandlerMethodArgumentResolver{

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(LoginUserId.class) &&
			parameter.getParameterType().equals(Long.class);
	}

	@Override
	public Object resolveArgument(MethodParameter
		parameter,
		ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest,
		WebDataBinderFactory binderFactory) throws Exception
	{
		return Long.parseLong("1");
	}
}
