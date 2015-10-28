package io.ehdev.conrad.app.gradle;

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
