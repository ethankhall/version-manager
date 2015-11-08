package io.ehdev.conrad.app.test;

import io.ehdev.conrad.app.service.GlobalControllerExceptionHandler;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.ExceptionHandlerMethodResolver;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

import java.lang.reflect.Method;

public class MvcControllerCreator {

    public static MockMvc createMockMvc(Object... controllers) {
        return MockMvcBuilders.standaloneSetup(controllers)
            .setHandlerExceptionResolvers(createExceptionResolver())
            .build();
    }

    private static ExceptionHandlerExceptionResolver createExceptionResolver() {
        ExceptionHandlerExceptionResolver exceptionResolver = new ExceptionHandlerExceptionResolver() {
            protected ServletInvocableHandlerMethod getExceptionHandlerMethod(HandlerMethod handlerMethod, Exception exception) {
                Method method = new ExceptionHandlerMethodResolver(GlobalControllerExceptionHandler.class).resolveMethod(exception);
                return new ServletInvocableHandlerMethod(new GlobalControllerExceptionHandler(), method);
            }
        };
        exceptionResolver.afterPropertiesSet();
        return exceptionResolver;
    }
}
