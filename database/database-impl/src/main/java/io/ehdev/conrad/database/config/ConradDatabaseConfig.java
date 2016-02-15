package io.ehdev.conrad.database.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.ehdev.conrad.database.config.transaction.SpringTransactionProvider;
import org.jooq.ConnectionProvider;
import org.jooq.SQLDialect;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

import javax.sql.DataSource;

@Configuration
@ComponentScan({"io.ehdev.conrad.database"})
@Import({ConradDoaConfig.class})
public class ConradDatabaseConfig {

    @Autowired
    Environment environment;

    @Bean
    DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(environment.getRequiredProperty("spring.datasource.url"));
        config.setUsername(environment.getRequiredProperty("spring.datasource.username"));
        config.setPassword(environment.getRequiredProperty("spring.datasource.password"));
        return new TransactionAwareDataSourceProxy(new HikariDataSource(config));
    }

    @Bean
    DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public ConnectionProvider connectionProvider() {
        return new DataSourceConnectionProvider(dataSource());
    }

    @Bean
    public DefaultDSLContext defaultDSLContext() {
        return new DefaultDSLContext(jooqConfiguration());
    }

    @Bean
    public org.jooq.Configuration jooqConfiguration() {
        return new DefaultConfiguration()
            .derive(connectionProvider())
            .derive(springTransactionProvider())
            .derive(SQLDialect.POSTGRES_9_4);
    }

    @Bean
    public SpringTransactionProvider springTransactionProvider() {
        return new SpringTransactionProvider(transactionManager());
    }
}
