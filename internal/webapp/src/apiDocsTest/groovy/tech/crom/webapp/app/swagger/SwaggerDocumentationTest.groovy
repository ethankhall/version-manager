package tech.crom.webapp.app.swagger

import org.apache.http.client.fluent.Request
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.env.Environment
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.TestPropertySource
import spock.lang.Specification

@WithMockUser
@TestPropertySource("/application-test.yml")
@SpringBootTest(classes = [SwaggerWebAppConfiguration], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SwaggerDocumentationTest extends Specification {

    @Autowired
    Environment environment

    def 'can get swagger docs'() {
        def builder = Request.Get("http://localhost:${ environment.getProperty("local.server.port") }/v2/api-docs")

        when:
        def response = builder.execute().returnResponse()
        new File(System.getProperty('swagger.docs')).text = response.entity.content.text

        then:
        noExceptionThrown()
    }
}
