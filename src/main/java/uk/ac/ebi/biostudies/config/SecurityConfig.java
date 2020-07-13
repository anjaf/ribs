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

    public String getProfileUrl() {
        return profileUrl;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public String getAdminIPAllowList() {
        return adminIPAllowList;
    }

}
