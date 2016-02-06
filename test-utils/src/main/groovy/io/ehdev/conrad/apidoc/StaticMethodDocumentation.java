package io.ehdev.conrad.apidoc;

public class StaticMethodDocumentation {
    public static ObjectDocumentationSnippet objectSnippits(DocObjectDefinition... definitions) {
        return new ObjectDocumentationSnippet("rest-object-model", definitions);
    }

    public static DocObjectDefinition documentObject(Object example, DocFieldDefinition... fields) {
        return new DocObjectDefinition(example, fields);
    }

    public static DocFieldDefinition fieldDocumentation(String path) {
        return new DocFieldDefinition(path);
    }
}
