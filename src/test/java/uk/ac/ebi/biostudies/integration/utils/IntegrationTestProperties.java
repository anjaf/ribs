package uk.ac.ebi.biostudies.integration.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

/**
 * Created by ehsan on 03/07/2017.
 */



@Configuration
@PropertySource("classpath:test.properties")
public class IntegrationTestProperties {

    @Value("${test.integration.username}")
    private String username;

    @Value("${test.integration.baseurl}")
    private String baseUrl;

    public String getUsername(){
        return username;
    }

    public String getBaseUrl(int randomPort){return String.format(baseUrl, randomPort);}
    public String getBaseUrl(){return baseUrl;}

}
