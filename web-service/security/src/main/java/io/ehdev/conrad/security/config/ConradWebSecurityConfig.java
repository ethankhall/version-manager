package io.ehdev.conrad.security.config;

import io.ehdev.conrad.security.filter.StatelessAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

@Order(102)
@Configuration
@EnableWebSecurity
public class ConradWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    StatelessAuthenticationFilter statelessAuthenticationFilter;

    @Autowired
    public void registerAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //@formatter:off
        http
            .formLogin()
                .loginPage("/signin")
                .loginProcessingUrl("/signin/authenticate")
                .failureUrl("/signin?param.error=bad_credentials")
                .and()
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .and()
            .addFilterBefore(statelessAuthenticationFilter, AbstractPreAuthenticatedProcessingFilter.class);
        //@formatter:on
//        final SpringSocialConfigurer socialConfigurer = new SpringSocialConfigurer();
//        socialConfigurer.addObjectPostProcessor(new ObjectPostProcessor<SocialAuthenticationFilter>() {
//            @Override
//            public <O extends SocialAuthenticationFilter> O postProcess(O socialAuthenticationFilter) {
//                socialAuthenticationFilter.setAuthenticationSuccessHandler(socialAuthenticationSuccessHandler);
//                return socialAuthenticationFilter;
//            }
//        });
//
//        http// add custom authentication filter for complete stateless JWT based authentication
//            .addFilterBefore(statelessAuthenticationFilter, AbstractPreAuthenticatedProcessingFilter.class)
//
//            // apply the configuration from the socialConfigurer (adds the SocialAuthenticationFilter)
//            .apply(socialConfigurer.userIdSource(userIdSource));
    }

}
