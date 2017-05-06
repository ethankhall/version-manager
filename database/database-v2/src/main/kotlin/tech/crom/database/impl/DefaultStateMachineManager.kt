package tech.crom.database.impl

import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import tech.crom.database.api.StateMachineManager
import tech.crom.db.Tables
import tech.crom.db.tables.records.VersionStateMachineStatesRecord
import tech.crom.model.repository.CromRepo
import tech.crom.model.state.StateMachineDefinition
import tech.crom.model.state.StateTransitions

@Service
open class DefaultStateMachineManager @Autowired constructor(
    val dslContext: DSLContext
) : StateMachineManager {

    private val vsmDefinitionTable = Tables.VERSION_STATE_MACHINE_DEFINITIONS.`as`("def")
    private val vsmConnectionsTable = Tables.VERSION_STATE_MACHINE_CONNECTIONS.`as`("conn")
    private val vsmStatesTable = Tables.VERSION_STATE_MACHINE_STATES.`as`("states")

    override fun getStateMachine(repo: CromRepo): StateMachineDefinition {
        val definition = dslContext.selectFrom(vsmDefinitionTable)
            .where(vsmDefinitionTable.REPO_DETAIL_ID.eq(repo.repoId))
            .fetchOne()
            .into(vsmDefinitionTable)

        val states = dslContext.selectFrom(vsmStatesTable)
            .where(vsmStatesTable.VERSION_STATE_MACHINE_ID.eq(definition.versionStateMachineId))
            .fetch()
            .into(vsmStatesTable)

        val stateLookup: Map<Long, VersionStateMachineStatesRecord> = states.associateBy({ it.versionStateMachineId }, { it })

        val connections = dslContext.selectFrom(vsmConnectionsTable)
            .where(vsmConnectionsTable.VERSION_STATE_MACHINE_ID.eq(definition.versionStateMachineId))
            .fetch()
            .into(vsmConnectionsTable)

        val stateTransition = states.associate { state ->
            val destinations = connections.filter { con -> con.source == state.versionStateMachineStateId }
                .map { con -> stateLookup[con.destination]!!.stateName }
            state.stateName to StateTransitions(destinations, if (state.autoTransition) state.nextState else null)
        }

        return StateMachineDefinition(definition.initialState, stateTransition)
    }

    override fun updateStateMachine(repo: CromRepo, stateMachine: StateMachineDefinition) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
