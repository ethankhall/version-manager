package io.ehdev.conrad.database.model.project;

import java.util.ArrayList;
import java.util.List;

public class ApiProjectDetails {

    String name;

    List<ApiProjectRepositoryDetails> details = new ArrayList<>();

    public ApiProjectDetails(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ApiProjectRepositoryDetails> getDetails() {
        return details;
    }

    public void addDetails(ApiProjectRepositoryDetails details) {
        this.details.add(details);
    }
}
