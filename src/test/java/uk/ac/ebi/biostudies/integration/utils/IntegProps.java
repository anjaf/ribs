package uk.ac.ebi.biostudies.integration.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

/**
 * Created by ehsan on 03/07/2017.
 */



@Configuration
@PropertySource("classpath:test.properties")
public class IntegProps {

    @Value("${test.integration.username}")
    private String username;
    @Value("${test.integration.password}")
    private String password;
    @Value("${test.integration.baseurl}")
    private String baseUrl;
    @Value("${test.integration.hosturl}")
    private String hostUrl;
    @Value("${test.file.dir}")
    private String testFileDir;

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public String getBaseUrl(){return baseUrl;}

    public String getHostUrl(){return  hostUrl;}

    public String getTestFileDir(){return testFileDir;}

}
