package tech.crom.config.metrics

import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Timer
import org.jooq.ExecuteContext
import org.jooq.Query
import org.jooq.impl.DefaultExecuteListener
import org.springframework.core.env.Environment
import tech.crom.logger.getLogger

class JooqMetricsCollector(metrics: MeterRegistry, env: Environment) : DefaultExecuteListener() {

    private val localTimer: ThreadLocal<LocalTimer>

    init {
        val responses = metrics.timer(JooqMetricsCollector::class.java.simpleName + "-queries")
        localTimer = ThreadLocal.withInitial {
            LocalTimer(responses, env.getProperty("metrics.jooq.slowQuery", Long::class.java, 25))
        }
    }

    override fun start(ctx: ExecuteContext) {
        localTimer.get().start()
    }

    override fun executeEnd(ctx: ExecuteContext) {
        localTimer.get().end { ctx.query() }
    }

    class LocalTimer(private val timer: Timer, private val slowQueryTimeout: Long) {
        private val log by getLogger()

        private var instance: Timer.Sample? = null
        fun start() {
            instance = Timer.start()
        }

        fun end(unit: () -> Query?) {
            val query = unit.invoke() ?: return
            val time = instance?.stop(timer) ?: return
            val ms = time / 1000000
            if (query.sql != null && ms >= slowQueryTimeout) {
                log.info("Long running ({} ms) sql query `{}`", ms, query.sql)
            }
        }
    }
}
