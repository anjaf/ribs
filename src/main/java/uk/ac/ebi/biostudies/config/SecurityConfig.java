package uk.ac.ebi.biostudies.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by ehsan on 16/03/2017.
 */

@Configuration
@PropertySource("classpath:security.properties")
public class SecurityConfig {

    @Value("${bs.users.authentication-url}")
    private String authUrl;

    public String getAuthUrl() {
        return authUrl;
    }
}
