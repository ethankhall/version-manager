package tech.crom.config

import org.jooq.Transaction
import org.jooq.TransactionContext
import org.slf4j.LoggerFactory
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.transaction.TransactionDefinition
import org.springframework.transaction.TransactionStatus
import org.springframework.transaction.support.DefaultTransactionDefinition

internal class SpringTransactionProvider(private val txMgr: DataSourceTransactionManager) : org.jooq.TransactionProvider {

    override fun begin(ctx: TransactionContext) {
        logger.debug("Begin transaction")

        // This TransactionProvider behaves like jOOQ's DefaultTransactionProvider,
        // which supports nested transactions using Savepoints
        val tx = txMgr.getTransaction(DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_NESTED))
        ctx.transaction(SpringTransaction(tx))
    }

    override fun commit(ctx: TransactionContext) {
        logger.debug("commit transaction")

        txMgr.commit((ctx.transaction() as SpringTransaction).tx)
    }

    override fun rollback(ctx: TransactionContext) {
        logger.debug("rollback transaction")

        txMgr.rollback((ctx.transaction() as SpringTransaction).tx)
    }

    internal inner class SpringTransaction(val tx: TransactionStatus) : Transaction

    companion object {
        private val logger = LoggerFactory.getLogger(SpringTransactionProvider::class.java)
    }
}
