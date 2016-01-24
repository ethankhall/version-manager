package io.ehdev.conrad.database.api.internal

import io.ehdev.conrad.database.config.TestConradDatabaseConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

import javax.transaction.Transactional

@Transactional
@ContextConfiguration(classes = [TestConradDatabaseConfig], loader = SpringApplicationContextLoader)
class DefaultUserManagementApiTest extends Specification {

    @Autowired
    DefaultUserManagementApi userManagementApi

    def 'can insert user'() {
        when:
        def user = userManagementApi.createUser('name', 'email')

        then:
        user != null
        user.uuid != null
        user.name == 'name'
        user.email == 'email'
    }
}
