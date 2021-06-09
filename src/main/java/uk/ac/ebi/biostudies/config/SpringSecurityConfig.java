package uk.ac.ebi.biostudies.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import uk.ac.ebi.biostudies.auth.BioStudiesLogoutHandler;
import uk.ac.ebi.biostudies.auth.CustomRememberMeCookieService;
import uk.ac.ebi.biostudies.auth.RefererAuthenticationSuccessHandler;
import uk.ac.ebi.biostudies.auth.RestBasedAuthenticationProvider;


@Configuration
@EnableWebSecurity
@ComponentScan("uk.ac.ebi.biostudies.config")
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private RestBasedAuthenticationProvider authProvider;
    @Autowired
    private CustomRememberMeCookieService customRememberMeCookieService;
    @Autowired
    private BioStudiesLogoutHandler bioStudiesLogoutHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().anonymous().principal("guest").authorities("GUEST").and()
                .authorizeRequests().antMatchers("/api/v1/index/**").access("hasIpAddress('localhost') or hasIpAddress('127.0.0.1') or hasIpAddress('0:0:0:0:0:0:0:1') or hasAuthority('SUPER_USER')")
                .antMatchers("/**").permitAll()
                .and().formLogin().successHandler(new RefererAuthenticationSuccessHandler())
                .loginPage("/").usernameParameter("u").passwordParameter("p").permitAll()
                .and().logout().logoutUrl("/logout").addLogoutHandler(bioStudiesLogoutHandler).deleteCookies("JSESSIONID")
                .and().rememberMe().rememberMeServices(customRememberMeCookieService);


    }


}
