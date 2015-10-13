package io.ehdev.conrad.app.service.repo.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotNull;

public class RepoCreateModel {

    @NotNull
    private String name;

    private String url;

    @NotNull
    private String bumper;

    public RepoCreateModel(String name, String url, String bumper) {
        this.name = name;
        this.url = url;
        this.bumper = bumper;
    }

    public RepoCreateModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBumper() {
        return bumper;
    }

    public void setBumper(String bumper) {
        this.bumper = bumper;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("name", name)
            .append("url", url)
            .append("bumper", bumper)
            .toString();
    }
}
