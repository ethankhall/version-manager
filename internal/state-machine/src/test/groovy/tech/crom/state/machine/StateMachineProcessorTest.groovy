package tech.crom.state.machine

import spock.lang.Specification
import tech.crom.model.state.StateMachineDefinition
import tech.crom.model.state.StateTransitions
import tech.crom.state.machine.exception.IllegalStateTransitionException
import tech.crom.state.machine.exception.UnknownStateException

class StateMachineProcessorTest extends Specification {

    StateMachineProcessor createStateMachineProcessor() {
        def stateDef = new StateMachineDefinition('default', [
            'default'   : new StateTransitions(['next', 'auto', 'auto-first'], null),
            'next'      : new StateTransitions([], null),
            'auto'      : new StateTransitions(['next'], 'next'),
            'auto-first': new StateTransitions(['auto'], 'auto')
        ])

        return new StateMachineProcessor(stateDef)
    }

    def 'can move between states'() {
        given:
        def stateMachineProcessor = createStateMachineProcessor()

        when:
        def transitions = stateMachineProcessor.doTransition('default', 'next')

        then:
        transitions.size() == 1
        transitions.first().source == 'default'
        transitions.first().target == 'next'
    }

    def 'will throw if unable to move to new state'() {
        given:
        def stateMachineProcessor = createStateMachineProcessor()

        when:
        stateMachineProcessor.doTransition('default', 'default')

        then:
        def e = thrown(IllegalStateTransitionException)
        e.message == 'Illegal next state default'
    }

    def 'will auto move into next state'() {
        given:
        def stateMachineProcessor = createStateMachineProcessor()

        when:
        def transition = stateMachineProcessor.doTransition('default', 'auto-first')

        then:
        transition.size() == 3
        transition[2].target == 'auto-first'
        transition[2].source == 'default'

        transition[1].target == 'auto'
        transition[1].source == 'auto-first'

        transition[0].target == 'next'
        transition[0].source == 'auto'
    }

    def 'will throw unknown state exception'() {
        given:
        def stateMachineProcessor = createStateMachineProcessor()

        when:
        stateMachineProcessor.doTransition('default', 'illegal')

        then:
        def e = thrown(UnknownStateException)
        e.message == 'Unknown state illegal'

        when:
        stateMachineProcessor.doTransition('missing', 'default')

        then:
        e = thrown(UnknownStateException)
        e.message == 'Unknown state missing'
    }
}
