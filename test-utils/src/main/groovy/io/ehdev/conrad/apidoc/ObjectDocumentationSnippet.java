package io.ehdev.conrad.apidoc;

import org.springframework.restdocs.operation.Operation;
import org.springframework.restdocs.snippet.TemplatedSnippet;

import java.util.*;

public class ObjectDocumentationSnippet extends TemplatedSnippet {

    private final List<DocObjectDefinition> definitions;

    /**
     * Creates a new {@code TemplatedSnippet} that will produce a snippet with the given
     * {@code snippetName}. The given {@code attributes} will be included in the model
     * during rendering of the template.
     *
     * @param snippetName The name of the snippet
     * @param definitions  The definitions
     */
    public ObjectDocumentationSnippet(String snippetName, DocObjectDefinition... definitions) {
        super(snippetName, null);
        this.definitions = Arrays.asList(definitions);
    }

    @Override
    protected Map<String, Object> createModel(Operation operation) {
        HashMap<String, Object> model = new HashMap<>();
        List<Map> fields = new ArrayList<>();
        model.put("types", fields);

        definitions.forEach(it -> fields.add(objectDefinition(it)));

        return model;
    }

    private Map<String, Object> objectDefinition(DocObjectDefinition definition) {
        HashMap<String, Object> fields = new HashMap<>();
        fields.put("className", definition.getClassName());
        fields.put("exampleObject", definition.getJson());
        fields.put("fields", fieldDefinitions(definition.getDefinitions()));
        return fields;
    }

    private List<Object> fieldDefinitions(List<DocFieldDefinition> definitions) {
        List<Object> fieldList = new ArrayList<>();
        for (DocFieldDefinition definition : definitions) {
            HashMap<String, Object> field = new HashMap<>();
            field.put("path", definition.getPath());
            field.put("description", definition.getDescription());
            field.put("type", definition.getType());
            fieldList.add(field);
        }
        return fieldList;
    }

}
