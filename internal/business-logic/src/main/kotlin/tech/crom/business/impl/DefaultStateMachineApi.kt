package tech.crom.business.impl

import org.springframework.stereotype.Service
import tech.crom.business.api.StateMachineApi
import tech.crom.model.commit.VersionDetails
import tech.crom.model.repository.CromRepo
import tech.crom.model.state.StateTransitions

@Service
class DefaultStateMachineApi : StateMachineApi {
    override fun getStateMachine(repo: CromRepo): StateTransitions {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateStateMachine(repo: CromRepo, transitions: StateTransitions) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun transitionVersion(repo: CromRepo, versionDetails: VersionDetails, nextState: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
