package io.ehdev.conrad.service.api.service.advice;

import io.ehdev.conrad.database.api.PermissionManagementApi;
import io.ehdev.conrad.database.model.ApiParameterContainer;
import io.ehdev.conrad.database.model.user.ApiUserPermission;
import io.ehdev.conrad.model.AdminView;
import io.ehdev.conrad.model.DefaultResourceSupport;
import io.ehdev.conrad.service.api.config.ApiParameterContainerBuilder;
import io.ehdev.conrad.service.api.service.advice.link.DefaultLinkControllerAdviceHelper;
import io.ehdev.conrad.service.api.service.annotation.InternalLinks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMappingJacksonResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static io.ehdev.conrad.service.api.service.advice.link.DefaultLinkControllerAdviceHelper.getFullURL;

@ControllerAdvice
public class CustomControllerAdvice extends AbstractMappingJacksonResponseBodyAdvice {

    private static final Logger logger = LoggerFactory.getLogger(CustomControllerAdvice.class);

    private final PermissionManagementApi permissionManagementApi;

    @Autowired
    public CustomControllerAdvice(PermissionManagementApi permissionManagementApi) {
        this.permissionManagementApi = permissionManagementApi;
    }

    @SuppressWarnings("unchecked")
    private Map<String, String> getParameters(HttpServletRequest servletRequest) {
        return (Map<String, String>) servletRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return super.supports(returnType, converterType) && ResponseEntity.class.isAssignableFrom(returnType.getParameterType());
    }

    @Override
    protected void beforeBodyWriteInternal(MappingJacksonValue bodyContainer, MediaType contentType, MethodParameter returnType, ServerHttpRequest request, ServerHttpResponse response) {
        if(!(bodyContainer.getValue() instanceof DefaultResourceSupport)) {
            return;
        }

        DefaultResourceSupport resourceSupport = (DefaultResourceSupport) bodyContainer.getValue();
        HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
        Map<String, String> parameters = getParameters(servletRequest);

        ApiParameterContainer container = new ApiParameterContainerBuilder(request.getPrincipal(), parameters).createContainer();
        ApiUserPermission permission = permissionManagementApi.findHighestUserPermission(container.getUser(), container.getProjectName(), container.getRepoName());

        addLinks(resourceSupport, returnType, servletRequest, permission);

        if(permission.isHigherOrEqualTo(ApiUserPermission.ADMIN)) {
            bodyContainer.setSerializationView(AdminView.class);
        } else {
            bodyContainer.setSerializationView(Object.class);
        }
    }

    private void addLinks(DefaultResourceSupport resourceSupport, MethodParameter returnType, HttpServletRequest servletRequest, ApiUserPermission permission) {
        InternalLinks annotation = returnType.getMethodAnnotation(InternalLinks.class);
        if(annotation != null) {
            new DefaultLinkControllerAdviceHelper(resourceSupport, permission, getFullURL(servletRequest)).addLinks(annotation);
        }
    }
}
