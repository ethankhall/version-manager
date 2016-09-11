package io.ehdev.conrad.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.ServletContextInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public class CromWebApplicationInitializer implements ServletContextInitializer {

    private static final Logger logger = LoggerFactory.getLogger(CromWebApplicationInitializer.class);

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        servletContext.addListener(org.springframework.web.context.request.RequestContextListener.class);
    }
}
