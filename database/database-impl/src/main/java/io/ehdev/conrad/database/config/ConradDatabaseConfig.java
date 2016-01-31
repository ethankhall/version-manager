package io.ehdev.conrad.database.config;

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
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@ComponentScan({"io.ehdev.conrad.database"})
@Import({ConradDoaConfig.class})
public class ConradDatabaseConfig {

    @Autowired
    DataSource dataSource;

    @Bean
    PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public TransactionAwareDataSourceProxy transactionAwareDataSource() {
        return new TransactionAwareDataSourceProxy(dataSource);
    }

    @Bean
    public ConnectionProvider connectionProvider() {
        return new DataSourceConnectionProvider(transactionAwareDataSource());
    }

    @Bean
    public DefaultDSLContext defaultDSLContext() {
        return new DefaultDSLContext(jooqConfiguration());
    }

    @Bean
    public org.jooq.Configuration jooqConfiguration() {
        return new DefaultConfiguration()
            .derive(connectionProvider())
            .derive(SQLDialect.POSTGRES_9_4);
    }
}
