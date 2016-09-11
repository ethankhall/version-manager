package tech.crom.config

import com.codahale.metrics.MetricRegistry
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.TransactionProvider
import org.jooq.impl.DataSourceConnectionProvider
import org.jooq.impl.DefaultConfiguration
import org.jooq.impl.DefaultDSLContext
import org.jooq.impl.DefaultExecuteListenerProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.core.env.Environment
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy
import tech.crom.config.metrics.JooqMetricsCollector
import javax.sql.DataSource

@Configuration
@ComponentScan("tech.crom.database")
@Import(MetricsConfiguration::class)
open class DatabaseConfig {

    @Autowired
    internal var environment: Environment? = null

    @Autowired
    internal var metricRegistry: MetricRegistry? = null

    @Bean
    open fun dataSource(): DataSource {
        val config = HikariConfig()
        config.jdbcUrl = environment!!.getRequiredProperty("spring.datasource.url")
        config.username = environment!!.getRequiredProperty("spring.datasource.username")
        config.password = environment!!.getRequiredProperty("spring.datasource.password")
        config.leakDetectionThreshold = 2000
        config.metricRegistry = metricRegistry
        return TransactionAwareDataSourceProxy(HikariDataSource(config))
    }

    @Bean
    open fun transactionManager(): DataSourceTransactionManager {
        return DataSourceTransactionManager(dataSource())
    }

    @Bean
    open fun connectionProvider(): org.jooq.ConnectionProvider {
        return DataSourceConnectionProvider(dataSource())
    }

    @Bean
    open fun defaultDSLContext(): DSLContext {
        return DefaultDSLContext(jooqConfiguration())
    }

    fun jooqConfiguration(): org.jooq.Configuration {
        return DefaultConfiguration()
            .derive(connectionProvider())
            .derive(transactionProvider())
            .derive(SQLDialect.POSTGRES_9_4)
            .derive(DefaultExecuteListenerProvider(JooqMetricsCollector(metricRegistry!!, environment!!)))
    }

    @Bean
    open fun transactionProvider(): TransactionProvider {
        return SpringTransactionProvider(transactionManager())
    }
}
