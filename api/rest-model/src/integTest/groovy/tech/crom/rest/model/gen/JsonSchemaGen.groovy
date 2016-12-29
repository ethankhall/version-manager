package tech.crom.rest.model.gen

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.kjetland.jackson.jsonSchema.JsonSchemaConfig
import com.kjetland.jackson.jsonSchema.JsonSchemaGenerator
import org.reflections.Reflections
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll
import tech.crom.rest.model.InputModel
import tech.crom.rest.model.OutputModel

class JsonSchemaGen extends Specification {

    @Shared
    def outputDir = new File(System.getenv("OUTPUT_DIR"))

    static String buildSchema(Class clazz) {
        def om = new ObjectMapper().registerModules(new KotlinModule(), new JavaTimeModule())
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        JsonSchemaGenerator html5 = new JsonSchemaGenerator(om, JsonSchemaConfig.vanillaJsonSchemaDraft4() );
        def jsonNode = html5.generateJsonSchema(clazz, clazz.simpleName, null)
        ((ObjectNode)jsonNode).put("javaName", clazz.simpleName)
        return om.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode)
    }

    @Unroll
    def 'process input #clazz'() {
        when:
        makeFile(clazz).text = buildSchema(clazz)

        then:
        true

        where:
        clazz << buildClasses(InputModel)
    }

    @Unroll
    def 'process output #clazz'() {
        when:
        makeFile(clazz).text = buildSchema(clazz)

        then:
        true

        where:
        clazz << buildClasses(OutputModel)
    }

    File makeFile(Class clazz) {
        def file = new File(outputDir, "${clazz.simpleName}.json")
        file.parentFile.mkdirs()

        return file
    }

    Set<Class<?>> buildClasses(Class clazz) {
        Reflections reflections = new Reflections("tech.crom.rest.model");
        return reflections.getTypesAnnotatedWith(clazz);
    }
}
