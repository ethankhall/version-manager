package io.ehdev.conrad.authentication.config;

import io.ehdev.conrad.api.user.database.BaseUserRepository;
import io.ehdev.conrad.authentication.database.repositories.SecurityUserClientProfileModelRepository;
import io.ehdev.conrad.authentication.database.repositories.SecurityUserTokenModelRepository;
import io.ehdev.conrad.authentication.user.auth.ConradAuthenticationProvider;
import io.ehdev.conrad.authentication.user.auth.RememberMeServicesImpl;
import io.ehdev.conrad.authentication.user.filter.StatelessAuthenticationFilter;
import org.pac4j.core.client.Clients;
import org.pac4j.springframework.security.authentication.ClientAuthenticationProvider;
import org.pac4j.springframework.security.web.ClientAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.session.SessionFixationProtectionStrategy;

@Order(50)
@Configuration
@EnableWebSecurity
public class ConradWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    Clients clients;

    @Autowired
    RememberMeServicesImpl rememberMeServices;

    @Autowired
    StatelessAuthenticationFilter statelessAuthenticationFilter;

    @Autowired
    SecurityUserTokenModelRepository tokenModelRepository;

    @Autowired
    public void registerAuthentication(AuthenticationManagerBuilder auth, AuthenticationProvider authenticationProvider) throws Exception {
        auth
            .authenticationProvider(authenticationProvider);
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    ClientAuthenticationFilter clientAuthenticationFilter() throws Exception {
        ClientAuthenticationFilter filter = new ClientAuthenticationFilter("/callback");
        filter.setAuthenticationManager(authenticationManagerBean());
        filter.setSessionAuthenticationStrategy(new SessionFixationProtectionStrategy());
        filter.setClients(clients);
        filter.setRememberMeServices(rememberMeServices);
        return filter;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //@formatter:off
        http
            .addFilterBefore(statelessAuthenticationFilter, AnonymousAuthenticationFilter.class)
            .addFilterBefore(clientAuthenticationFilter(), AnonymousAuthenticationFilter.class)
            .rememberMe();
        //@formatter:on
    }

    @Bean
    AuthenticationProvider authenticationProvider(BaseUserRepository userModelRepository,
                                                  SecurityUserTokenModelRepository tokenModelRepository,
                                                  SecurityUserClientProfileModelRepository clientUserProfileModelRepository) {
        ClientAuthenticationProvider clientAuthenticationProvider = new ClientAuthenticationProvider();
        clientAuthenticationProvider.setClients(clients);

        return new ConradAuthenticationProvider(userModelRepository, clientAuthenticationProvider, tokenModelRepository, clientUserProfileModelRepository);
    }
}
