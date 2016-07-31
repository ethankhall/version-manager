package tech.crom.security.authentication.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.ObjectPostProcessor
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter
import org.springframework.social.UserIdSource
import org.springframework.social.security.SocialAuthenticationFilter
import org.springframework.social.security.SpringSocialConfigurer
import tech.crom.security.authentication.config.stateless.SocialAuthenticationSuccessHandler
import tech.crom.security.authentication.config.stateless.StatelessAuthenticationFilter

@Configuration
@EnableWebSecurity
@Order(value = -100)
open class WebSecurityConfig @Autowired constructor(
    val userDetailsService: UserDetailsService,
    val userIdSource: UserIdSource,
    val socialAuthenticationSuccessHandler: SocialAuthenticationSuccessHandler,
    val statelessAuthenticationFilter: StatelessAuthenticationFilter
) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        //@formatter:off
        http
            .formLogin()
                .loginPage("/signin")
                .loginProcessingUrl("/signin/authenticate")
                .failureUrl("/signin?param.error=bad_credentials")
            .and()
                .logout()
                    .logoutUrl("/signout")
                    .deleteCookies("JSESSIONID")
            .and()
                .authorizeRequests()
                    .antMatchers(
                        "/admin/**", "/favicon.ico", "/resources/**", "/signin", "/connect/**", "/auth/**").permitAll()
                    .antMatchers("/**").authenticated()
            .and()
                .rememberMe()
            .and()
                .apply(createSocialConfigurer())
            .and()
                .addFilterBefore(statelessAuthenticationFilter, AbstractPreAuthenticatedProcessingFilter::class.java)
        //@formatter:on
    }

    private fun createSocialConfigurer(): SpringSocialConfigurer {
        val socialConfigurer = SpringSocialConfigurer()
            .connectionAddedRedirectUrl("/")
            .userIdSource(userIdSource)

        socialConfigurer.addObjectPostProcessor(object : ObjectPostProcessor<SocialAuthenticationFilter> {
            override fun <O : SocialAuthenticationFilter> postProcess(filter: O): O {
                filter.setAuthenticationSuccessHandler(socialAuthenticationSuccessHandler)
                return filter
            }
        })

        return socialConfigurer
    }

    @Override
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService)
    }
}
