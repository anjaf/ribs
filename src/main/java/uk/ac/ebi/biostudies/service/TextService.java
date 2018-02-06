package uk.ac.ebi.biostudies.service;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;

@Service
@PropertySource("classpath:normalised-text.properties")
public class TextService {


    private Logger logger = LogManager.getLogger(TextService.class.getName());

    @Autowired
    Environment env;

    @PostConstruct
    void init() {
        logger.debug("Initialising TextService");
    }

    public String getNormalisedString(String string) {
        return env.containsProperty(string) ? env.getProperty(string)  : string;
    }

}
