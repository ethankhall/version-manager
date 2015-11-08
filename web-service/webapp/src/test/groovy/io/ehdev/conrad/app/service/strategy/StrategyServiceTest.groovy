package io.ehdev.conrad.app.service.strategy

import io.ehdev.conrad.app.manager.BumperManager
import io.ehdev.conrad.app.test.MvcControllerCreator
import io.ehdev.conrad.backend.version.bumper.SemanticVersionBumper
import io.ehdev.conrad.database.model.VersionBumperModel
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

class StrategyServiceTest extends Specification {

    MockMvc mockMvc
    StrategyService strategyService
    BumperManager bumperManager

    def setup() {
        bumperManager = Mock(BumperManager)
        strategyService = new StrategyService(bumperManager)
        mockMvc = MvcControllerCreator.createMockMvc(strategyService)
    }

    def 'can get all strategies'() {
        bumperManager.findAllVersionBumpers() >> [createBumper()]

        expect:
        mockMvc.perform(get('/api/strategy'))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath('$.SemanticVersionBumper.name').value('SemanticVersionBumper'))
            .andExpect(jsonPath('$.SemanticVersionBumper.description').value('SemanticVersionBumper'))
    }

    VersionBumperModel createBumper() {
        return new VersionBumperModel(SemanticVersionBumper.getName(), SemanticVersionBumper.getSimpleName(),
            SemanticVersionBumper.getSimpleName())
    }
}
