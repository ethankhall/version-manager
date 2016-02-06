package io.ehdev.conrad.apidoc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.io.StringWriter;
import java.io.UncheckedIOException;
import java.util.Arrays;
import java.util.List;

public class DocObjectDefinition {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String className;
    private final Object example;
    private final List<DocFieldDefinition> definitions;

    public DocObjectDefinition(Object object, DocFieldDefinition... definitions) {
        this.className = object.getClass().getSimpleName();
        this.definitions = Arrays.asList(definitions);
        this.example = object;
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public String getClassName() {
        return className;
    }

    public List<DocFieldDefinition> getDefinitions() {
        return definitions;
    }

    public String getJson() {
        try {
            StringWriter sw = new StringWriter();
            objectMapper.writeValue(sw, example);
            return sw.toString();
        } catch (IOException ioe) {
            throw new UncheckedIOException(ioe);
        }
    }
}
