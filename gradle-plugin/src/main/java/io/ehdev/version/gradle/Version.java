package io.ehdev.version.gradle;

public class Version {
    String text;

    public Version(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
