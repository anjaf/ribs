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

    @Value("${bs.users.auth-check-url}")
    private String authCheckUrl;

    @Value("${bs.users.login-url}")
    private String loginUrl;

    @Value("${index.admin.ip.whitelist}")
    private String adminIPWhitelist;

    public String getAuthCheckUrl() {
        return authCheckUrl;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public String getAdminIPWhitelist() {
        return adminIPWhitelist;
    }

}
