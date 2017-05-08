package tech.crom.database.impl

import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import tech.crom.database.api.StateMachineManager
import tech.crom.db.Tables
import tech.crom.db.tables.records.VersionStateMachineStatesRecord
import tech.crom.logger.getLogger
import tech.crom.model.repository.CromRepo
import tech.crom.model.state.StateMachineDefinition
import tech.crom.model.state.StateTransitions

@Service
open class DefaultStateMachineManager @Autowired constructor(
    val dslContext: DSLContext
) : StateMachineManager {

    val logger by getLogger()

    override fun getStateMachine(repo: CromRepo): StateMachineDefinition {
        val vsmDefinitionTable = Tables.VERSION_STATE_MACHINE_DEFINITIONS.`as`("def")
        val vsmConnectionsTable = Tables.VERSION_STATE_MACHINE_CONNECTIONS.`as`("conn")
        val vsmStatesTable = Tables.VERSION_STATE_MACHINE_STATES.`as`("states")

        val definition = dslContext.selectFrom(vsmDefinitionTable)
            .where(vsmDefinitionTable.REPO_DETAIL_ID.eq(repo.repoId))
            .fetchOne()
            .into(vsmDefinitionTable)

        val states = dslContext.selectFrom(vsmStatesTable)
            .where(vsmStatesTable.VERSION_STATE_MACHINE_ID.eq(definition.versionStateMachineId))
            .fetch()
            .into(vsmStatesTable)

        val stateLookup: Map<Long, VersionStateMachineStatesRecord> = states.associateBy({ it.versionStateMachineStateId }, { it })

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

    override fun registerNewStateMachine(repo: CromRepo) {
        setStateMachine(repo, StateMachineDefinition("DEFAULT", mapOf(Pair("DEFAULT", StateTransitions(emptyList(), null)))))
    }

    override fun updateStateMachine(repo: CromRepo, stateMachine: StateMachineDefinition, inplaceMigrations: Map<String, String>) {
        logger.info("Updating repo {}'s state machine", repo.repoId)
        val currentStateMachine = getStateMachine(repo)
        if (!inplaceMigrations.values.all { stateMachine.stateTransitions.keys.contains(it) }) {
            throw RuntimeException()
        }

        if (!currentStateMachine.stateTransitions.keys.all { inplaceMigrations.keys.contains(it) }) {
            throw RuntimeException()
        }

        val cd = Tables.COMMIT_DETAILS
        val currentlyUsedStates = dslContext.selectDistinct(cd.STATE)
            .from(cd)
            .where(cd.REPO_DETAIL_ID.eq(repo.repoId))
            .fetchInto(String::class.java)

        val unknownStates = currentlyUsedStates.filter {
            !stateMachine.stateTransitions.keys.contains(it) && !inplaceMigrations.keys.contains(it)
        }

        if (!unknownStates.isEmpty()) {
            throw RuntimeException(unknownStates.joinToString(","))
        }

        inplaceMigrations.forEach { key, value ->
            dslContext.update(cd)
                .set(cd.STATE, value)
                .where(cd.STATE.eq(key))
                .execute()
        }

        dropStateMachine(repo)
        setStateMachine(repo, stateMachine)
    }

    private fun setStateMachine(repo: CromRepo, stateMachine: StateMachineDefinition) {
        val vsmDefinitionTable = Tables.VERSION_STATE_MACHINE_DEFINITIONS
        val vsmStatesTable = Tables.VERSION_STATE_MACHINE_STATES
        val vsmConnectionsTable = Tables.VERSION_STATE_MACHINE_CONNECTIONS

        val id = dslContext.insertInto(vsmDefinitionTable)
            .columns(vsmDefinitionTable.REPO_DETAIL_ID, vsmDefinitionTable.INITIAL_STATE)
            .values(repo.repoId, stateMachine.defaultState)
            .returning()
            .fetchOne()
            .into(vsmDefinitionTable)

        var stateQuery = dslContext.insertInto(vsmStatesTable)
            .columns(vsmStatesTable.VERSION_STATE_MACHINE_ID, vsmStatesTable.STATE_NAME,
                vsmStatesTable.AUTO_TRANSITION, vsmStatesTable.NEXT_STATE)

        stateMachine.stateTransitions.forEach { key, transitions ->
            stateQuery = stateQuery.values(id.versionStateMachineId, key,
                transitions.forceTransition != null, transitions.forceTransition)
        }

        stateQuery.execute()

        val stateMap = dslContext.selectFrom(vsmStatesTable)
            .where(vsmStatesTable.VERSION_STATE_MACHINE_ID.eq(id.versionStateMachineId))
            .fetch()
            .into(vsmStatesTable)
            .associate { Pair(it.stateName, it.versionStateMachineStateId) }

        var connectionQuery = dslContext.insertInto(vsmConnectionsTable)
            .columns(vsmConnectionsTable.VERSION_STATE_MACHINE_ID, vsmConnectionsTable.SOURCE, vsmConnectionsTable.DESTINATION)

        stateMachine.stateTransitions.forEach { key, (nextStates) ->
            nextStates.forEach { destinationState ->
                connectionQuery = connectionQuery.values(id.versionStateMachineId, stateMap[key], stateMap[destinationState])
            }
        }
        connectionQuery.execute()
    }

    private fun dropStateMachine(repo: CromRepo) {
        val vsmDefinitionTable = Tables.VERSION_STATE_MACHINE_DEFINITIONS.`as`("def")
        val vsmStatesTable = Tables.VERSION_STATE_MACHINE_STATES.`as`("states")
        val vsmConnectionsTable = Tables.VERSION_STATE_MACHINE_CONNECTIONS.`as`("conn")

        val definition = dslContext.selectFrom(vsmDefinitionTable)
                .where(vsmDefinitionTable.REPO_DETAIL_ID.eq(repo.repoId))
                .fetchOne()
                .into(vsmDefinitionTable)

        dslContext.deleteFrom(vsmConnectionsTable)
                .where(vsmConnectionsTable.VERSION_STATE_MACHINE_ID.eq(definition.versionStateMachineId))
                .execute()

        dslContext.deleteFrom(vsmStatesTable)
                .where(vsmStatesTable.VERSION_STATE_MACHINE_ID.eq(definition.versionStateMachineId))
                .execute()

        dslContext.deleteFrom(vsmDefinitionTable)
                .where(vsmDefinitionTable.VERSION_STATE_MACHINE_ID.eq(definition.versionStateMachineId))
                .execute()
    }
}
