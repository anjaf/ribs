package uk.ac.ebi.biostudies.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by ehsan on 16/03/2017.
 */

@Configuration
@PropertySource("classpath:orcid.properties")
public class OrcidConfig {

    @Value("${dataclaiming.url}")
    private String dataClaimingUrl;

    public String getdataClaimingUrl() {
        return dataClaimingUrl;
    }
}
