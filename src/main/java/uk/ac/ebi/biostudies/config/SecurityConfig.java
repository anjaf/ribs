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

    @Value("${auth.profileUrl}")
    private String profileUrl;

    @Value("${auth.loginUrl}")
    private String loginUrl;

    @Value("${index.admin.ip.allow.list}")
    private String adminIPAllowList;

    @Value("${aspera.user}")
    private String asperaUser;

    @Value("${aspera.pass}")
    private String asperaPass;

    @Value("${aspera.token.server}")
    private String asperaTokenServer;

    public String getProfileUrl() {
        return profileUrl;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public String getAdminIPAllowList() {
        return adminIPAllowList;
    }

    public String getAsperaUser() {
        return asperaUser;
    }

    public String getAsperaPass() {
        return asperaPass;
    }

    public String getAsperaTokenServer() {
        return asperaTokenServer;
    }
}
