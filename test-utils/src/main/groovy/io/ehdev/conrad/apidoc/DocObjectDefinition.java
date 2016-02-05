package io.ehdev.conrad.apidoc;

import java.util.Arrays;
import java.util.List;

public class DocObjectDefinition {
    private final String className;
    private final List<DocFieldDefinition> definitions;

    public DocObjectDefinition(Class clazz, DocFieldDefinition... definitions) {
        this.className = clazz.getSimpleName();
        this.definitions = Arrays.asList(definitions);
    }

    public String getClassName() {
        return className;
    }

    public List<DocFieldDefinition> getDefinitions() {
        return definitions;
    }
}
