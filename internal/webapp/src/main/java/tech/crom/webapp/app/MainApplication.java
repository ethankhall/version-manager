package tech.crom.webapp.app;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import tech.crom.business.config.BusinessLogicConfig;
import tech.crom.config.ClockConfig;
import tech.crom.config.DatabaseConfig;
import tech.crom.config.SharedMasterConfig;
import tech.crom.config.SpringTransactionProvider;
import tech.crom.security.authentication.config.AuthenticationConfig;
import tech.crom.security.authorization.config.AuthorizationConfig;
import tech.crom.service.api.config.ServiceApiConfig;
import tech.crom.storage.config.StorageConfig;
import tech.crom.version.bumper.config.VersionBumperConfig;

@EnableCaching
@Configuration
@Import({DatabaseConfig.class, ClockConfig.class, SharedMasterConfig.class, SpringTransactionProvider.class,
    VersionBumperConfig.class, BusinessLogicConfig.class, AuthorizationConfig.class, AuthenticationConfig.class,
    ServiceApiConfig.class, StorageConfig.class})
@ComponentScan({"tech.crom.webapp.app", "tech.crom.webapp.endpoint"})
@EnableAutoConfiguration(exclude = {JdbcTemplateAutoConfiguration.class, DataSourceAutoConfiguration.class})
public class MainApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(new Object[]{MainApplication.class}, args);
    }

    @Bean
    public JettyEmbeddedServletContainerFactory jettyEmbeddedServletContainerFactory(Environment env) {
        Integer port = Integer.parseInt(env.getProperty("server.port"));
        Integer maxThreads = Integer.parseInt(env.getProperty("jetty.threadPool.maxThreads", "20"));
        Integer minThreads = Integer.parseInt(env.getProperty("jetty.threadPool.minThreads", "4"));
        Integer idleTimeout = Integer.parseInt(env.getProperty("jetty.threadPool.idleTimeout", "60000"));
        final JettyEmbeddedServletContainerFactory factory = new JettyEmbeddedServletContainerFactory(port);
        factory.addServerCustomizers((Server server) -> {
            final QueuedThreadPool threadPool = server.getBean(QueuedThreadPool.class);
            threadPool.setMinThreads(minThreads);
            threadPool.setMaxThreads(maxThreads);
            threadPool.setIdleTimeout(idleTimeout);
        });
        return factory;
    }
}
