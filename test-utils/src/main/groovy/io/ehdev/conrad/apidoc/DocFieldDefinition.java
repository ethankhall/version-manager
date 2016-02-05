package io.ehdev.conrad.apidoc;

public class DocFieldDefinition {
    private final String path;
    private String description;
    private String type;

    public DocFieldDefinition(String path) {
        this.path = path;
    }

    public DocFieldDefinition description(String description) {
        this.description = description;
        return this;
    }

    public DocFieldDefinition withType(Object type) {
        this.type = type.toString();
        return this;
    }

    public String getPath() {
        return path;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }
}
