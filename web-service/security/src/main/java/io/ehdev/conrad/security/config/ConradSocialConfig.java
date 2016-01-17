package io.ehdev.conrad.security.config;

import io.ehdev.conrad.security.user.UserSignInConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.mem.InMemoryUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.social.google.connect.GoogleConnectionFactory;

@EnableSocial
@Configuration
public class ConradSocialConfig extends SocialConfigurerAdapter {

    @Autowired
    UserSignInConnector userSignInConnector;

    public void addConnectionFactories(ConnectionFactoryConfigurer cfConfig, Environment environment) {
        cfConfig.addConnectionFactory(new GoogleConnectionFactory(
            environment.getProperty("google.clientId"),
            environment.getProperty("google.clientSecret")));
    }

    @Bean
    public ProviderSignInController providerSignInController(ConnectionFactoryLocator connectionFactoryLocator,
                                                             UsersConnectionRepository usersConnectionRepository) {
        return new ProviderSignInController(connectionFactoryLocator, usersConnectionRepository, userSignInConnector);
    }

    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        InMemoryUsersConnectionRepository connectionRepository = new InMemoryUsersConnectionRepository(connectionFactoryLocator);
        connectionRepository.setConnectionSignUp(userSignInConnector);
        return connectionRepository;
    }
}
