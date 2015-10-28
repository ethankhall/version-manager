package io.ehdev.conrad.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({VersionBackendConfiguration.class, CommitBackendConfiguration.class})
public class BackendConfiguration {
}
