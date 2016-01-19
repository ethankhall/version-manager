package io.ehdev.conrad.security.config;

import io.ehdev.conrad.security.database.repositories.SecurityUserModelRepository;
import io.ehdev.conrad.security.user.auth.ConradAuthenticationProvider;
import io.ehdev.conrad.security.user.filter.StatelessAuthenticationFilter;
import io.ehdev.conrad.security.user.create.CreateNewUserSessionStrategy;
import io.ehdev.conrad.security.user.auth.DelegatingSessionAuthenticationStrategy;
import io.ehdev.conrad.security.user.auth.RememberMeServicesImpl;
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
@EnableWebSecurity(debug = true)
public class ConradWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    Clients clients;

    @Autowired
    CreateNewUserSessionStrategy createNewUserSessionStrategy;

    @Autowired
    RememberMeServicesImpl rememberMeServices;

    @Autowired
    StatelessAuthenticationFilter statelessAuthenticationFilter;

    @Autowired
    SecurityUserModelRepository userModelRepository;

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
        DelegatingSessionAuthenticationStrategy strategy = new DelegatingSessionAuthenticationStrategy(
            createNewUserSessionStrategy,
            new SessionFixationProtectionStrategy());

        ClientAuthenticationFilter filter = new ClientAuthenticationFilter("/callback");
        filter.setAuthenticationManager(authenticationManagerBean());
        filter.setSessionAuthenticationStrategy(strategy);
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
    AuthenticationProvider authenticationProvider() {
        ClientAuthenticationProvider clientAuthenticationProvider = new ClientAuthenticationProvider();
        clientAuthenticationProvider.setClients(clients);

        return new ConradAuthenticationProvider(clientAuthenticationProvider, userModelRepository);
    }
}
