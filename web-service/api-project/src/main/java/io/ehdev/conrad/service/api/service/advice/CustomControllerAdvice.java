package io.ehdev.conrad.service.api.service.advice;

import io.ehdev.conrad.model.AdminView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMappingJacksonResponseBodyAdvice;
import tech.crom.model.security.authorization.CromPermission;
import tech.crom.service.api.config.RequestDetailsParameterResolver;
import tech.crom.web.api.model.RequestDetails;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class CustomControllerAdvice extends AbstractMappingJacksonResponseBodyAdvice {

    private final RequestDetailsParameterResolver requestDetailsParameterResolver;

    @Autowired
    public CustomControllerAdvice(RequestDetailsParameterResolver requestDetailsParameterResolver) {
        this.requestDetailsParameterResolver = requestDetailsParameterResolver;
    }
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return super.supports(returnType, converterType) && ResponseEntity.class.isAssignableFrom(returnType.getParameterType());
    }

    @Override
    protected void beforeBodyWriteInternal(MappingJacksonValue bodyContainer,
                                           MediaType contentType,
                                           MethodParameter returnType,
                                           ServerHttpRequest request,
                                           ServerHttpResponse response) {
        HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();

        RequestDetails requestDetails = requestDetailsParameterResolver.createRequestDetails(servletRequest);
        CromPermission permission = requestDetails.getRequestPermission().findHighestPermission();

        if(permission.isHigherOrEqualThan(CromPermission.ADMIN)) {
            bodyContainer.setSerializationView(AdminView.class);
        } else {
            bodyContainer.setSerializationView(Object.class);
        }
    }
}
