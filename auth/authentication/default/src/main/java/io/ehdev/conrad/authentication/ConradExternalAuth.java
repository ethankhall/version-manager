package io.ehdev.conrad.authentication;

import org.pac4j.core.authorization.RequireAnyRoleAuthorizer;
import org.pac4j.core.client.Clients;
import org.pac4j.core.config.Config;
import org.pac4j.oauth.client.GitHubClient;
import org.pac4j.oauth.client.Google2Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@ConditionalOnMissingBean(Config.class)
public class ConradExternalAuth {

    @Autowired
    Environment environment;

    @Bean
    public Config config() {
        Config config = new Config(clients());
        config.addAuthorizer("admin", new RequireAnyRoleAuthorizer("ROLE_ADMIN"));
        return config;
    }

    @Bean
    GitHubClient gitHubClient() {
        return new GitHubClient(environment.getRequiredProperty("auth.client.github.key"),
            environment.getRequiredProperty("auth.client.github.secret"));
    }

    @Bean
    Google2Client google2Client() {
        return new Google2Client(environment.getRequiredProperty("auth.client.google.key"),
            environment.getRequiredProperty("auth.client.google.secret"));
    }

    @Bean
    Clients clients() {
        return new Clients("http://localhost:8080/callback", gitHubClient(), google2Client());
    }
}
