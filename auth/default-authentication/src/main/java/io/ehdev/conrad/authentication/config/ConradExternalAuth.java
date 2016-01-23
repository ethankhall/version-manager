package io.ehdev.conrad.authentication.config;

import io.ehdev.conrad.authentication.jwt.JwtManager;
import org.pac4j.core.authorization.RequireAnyRoleAuthorizer;
import org.pac4j.core.client.Clients;
import org.pac4j.core.config.Config;
import org.pac4j.oauth.client.GitHubClient;
import org.pac4j.oauth.client.Google2Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ComponentScan(basePackages = "org.pac4j.springframework.web")
public class ConradExternalAuth extends WebMvcConfigurerAdapter {

    @Autowired
    JwtManager jwtManager;

    @Bean
    public Config config() {
        Config config = new Config(clients());
        config.addAuthorizer("admin", new RequireAnyRoleAuthorizer("ROLE_ADMIN"));
        return config;
    }

    @Bean
    GitHubClient gitHubClient() {
        return new GitHubClient("e26002913dce86419723", "c8d18cb9b65212ad8acb3d0603a85f6bc3aa704f");
    }

    @Bean
    Google2Client google2Client() {
        return new Google2Client("428210344673-bmar2c3qa5daop98pu9731l8v0tihrt1.apps.googleusercontent.com", "Y4UZtHiDP1R2o5_INu9MWykI");
    }

    @Bean
    Clients clients() {
        return new Clients("http://localhost:8080/callback", gitHubClient(), google2Client());
    }
}
