package io.ehdev.conrad.app.service;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import tech.crom.database.api.ProjectManager;

@Component
public class JooqInitialization implements ApplicationListener<ApplicationReadyEvent> {

    private final ProjectManager projectManager;

    public JooqInitialization(ProjectManager projectManager) {
        this.projectManager = projectManager;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        projectManager.findProject(""); //initialize db stuff
    }
}
