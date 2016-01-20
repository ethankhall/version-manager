package io.ehdev.conrad.api.user.config;

import io.ehdev.conrad.model.user.ConradUser;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class BaseUserModelResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(ConradUser.class);
    }

    @Override
    public ConradUser resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {
        if(webRequest.getUserPrincipal() instanceof Authentication) {
            Object principal = ((Authentication) webRequest.getUserPrincipal()).getPrincipal();
            if(principal instanceof ConradUser) {
                return (ConradUser) principal;
            }
        }
        return null;
    }
}
