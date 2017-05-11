package tech.crom.config.metrics

import com.codahale.metrics.MetricRegistry
import com.codahale.metrics.Timer
import org.jooq.ExecuteContext
import org.jooq.Query
import org.jooq.impl.DefaultExecuteListener
import org.springframework.core.env.Environment
import tech.crom.logger.getLogger

class JooqMetricsCollector(metrics: MetricRegistry, env: Environment) : DefaultExecuteListener() {

    val localTimer: ThreadLocal<LocalTimer>

    init {
        val responses = metrics.timer(MetricRegistry.name(JooqMetricsCollector::class.java, "queries"))
        localTimer = ThreadLocal.withInitial {
            LocalTimer(responses, env.getProperty("metrics.jooq.slowQuery", Long::class.java, 25))
        }
    }

    override fun start(ctx: ExecuteContext) {
        localTimer.get().start()
    }

    override fun executeEnd(ctx: ExecuteContext) {
        localTimer.get().end({ ctx.query() })
    }

    class LocalTimer(val timer: Timer, val slowQueryTimeout: Long) {
        val log by getLogger()

        var instance: Timer.Context? = null
        fun start() {
            instance = timer.time()
        }

        fun end(unit: () -> Query?) {
            val time = instance?.stop() ?: return
            val query = unit.invoke() ?: return
            val ms = time / 1000000
            if (query.sql != null && ms >= slowQueryTimeout) {
                log.info("Long running ({} ms) sql query `{}`", ms, query.sql)
            }
        }
    }
}
