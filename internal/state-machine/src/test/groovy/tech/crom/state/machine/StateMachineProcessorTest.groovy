package tech.crom.state.machine

import spock.lang.Specification
import tech.crom.state.machine.exception.IllegalStateTransitionException
import tech.crom.state.machine.exception.UnknownStateException
import tech.crom.state.machine.exception.UnknownVersionIdException
import tech.crom.model.state.StateMachineDefinition
import tech.crom.model.state.StateTransitions

class StateMachineProcessorTest extends Specification {

    StateMachineProcessor createStateMachineProcessor() {
        def stateDef = new StateMachineDefinition('default', [
            'default'   : new StateTransitions(['next', 'auto', 'auto-first'], null),
            'next'      : new StateTransitions([], null),
            'auto'      : new StateTransitions(['next'], 'next'),
            'auto-first': new StateTransitions(['auto'], 'auto')
        ])

        return new StateMachineProcessor(stateDef, [2L: 'default', 4L: 'auto', 6L: 'auto-first', 10L: 'illegal'])
    }

    def 'can move between states'() {
        given:
        def stateMachineProcessor = createStateMachineProcessor()

        when:
        def transitions = stateMachineProcessor.doTransition(2L, 'next')

        then:
        transitions.size() == 1
        transitions.first().source == 'default'
        transitions.first().target == 'next'
        transitions.first().versionId == 2

        stateMachineProcessor.currentStateMachine[2L] == 'next'
    }

    def 'will throw if unable to move to new state'() {
        given:
        def stateMachineProcessor = createStateMachineProcessor()

        when:
        stateMachineProcessor.doTransition(4L, 'default')

        then:
        def e = thrown(IllegalStateTransitionException)
        e.message == 'Illegal next state default'
    }

    def 'will auto move into next state'() {
        given:
        def stateMachineProcessor = createStateMachineProcessor()

        when:
        def transition = stateMachineProcessor.doTransition(2L, 'auto-first')

        then:
        transition.size() == 3
        transition[2].versionId == 2
        transition[2].target == 'auto-first'
        transition[2].source == 'default'

        transition[1].versionId == 6
        transition[1].target == 'auto'
        transition[1].source == 'auto-first'

        transition[0].versionId == 4
        transition[0].target == 'next'
        transition[0].source == 'auto'

        stateMachineProcessor.currentStateMachine[2L] == 'auto-first'
        stateMachineProcessor.currentStateMachine[6L] == 'auto'
        stateMachineProcessor.currentStateMachine[4L] == 'next'
    }

    def 'will throw unknown state exception'() {
        given:
        def stateMachineProcessor = createStateMachineProcessor()

        when:
        stateMachineProcessor.doTransition(10L, 'missing')

        then:
        def e = thrown(UnknownStateException)
        e.message == 'Unknown state illegal'

        when:
        stateMachineProcessor.doTransition(2L, 'missing')

        then:
        e = thrown(UnknownStateException)
        e.message == 'Unknown state missing'
    }

    def 'will throw on unknown versionId'() {
        given:
        def stateMachineProcessor = createStateMachineProcessor()

        when:
        stateMachineProcessor.doTransition(1L, 'missing')

        then:
        def e = thrown(UnknownVersionIdException)
        e.message == 'Unknown versionId 1'

    }

    def 'can add new version'() {
        given:
        def stateMachineProcessor = createStateMachineProcessor()

        when:
        def notifications = stateMachineProcessor.addVersion(1L)

        then:
        notifications.size() == 1
        notifications[0].versionId == 1
        notifications[0].source == null
        notifications[0].target == 'default'

        stateMachineProcessor.currentStateMachine[1L] == 'default'

        when:
        notifications = stateMachineProcessor.doTransition(1L, 'auto')

        then:
        notifications.size() == 2

        notifications[1].versionId == 1
        notifications[1].target == 'auto'
        notifications[1].source == 'default'

        notifications[0].versionId == 4
        notifications[0].target == 'next'
        notifications[0].source == 'auto'

        stateMachineProcessor.currentStateMachine[2L] == 'default'
        stateMachineProcessor.currentStateMachine[1L] == 'auto'
        stateMachineProcessor.currentStateMachine[4L] == 'next'

    }
}
