package tech.crom.webapp

import org.flywaydb.core.Flyway
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.actuate.health.Health
import org.springframework.boot.actuate.health.HealthIndicator
import org.springframework.stereotype.Component

@Component
open class FlywayMigrationHealthCheck @Autowired constructor(val flyway: Flyway) : HealthIndicator {

    override fun health(): Health {
        if (flyway.info().pending().isNotEmpty()) {
            return Health.down().withDetail("Database is out of date", flyway.info().current().version.version).build()
        }
        return Health.up().build()
    }

}
