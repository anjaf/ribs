package uk.ac.ebi.biostudies.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
@PropertySource("classpath:git.properties")
public class GitConfig {

    @Value("${git.commit.id.abbrev}")
    private String gitCommitIdAbbrev;

    public String getGitCommitIdAbbrev() {
        return gitCommitIdAbbrev;
    }
}
