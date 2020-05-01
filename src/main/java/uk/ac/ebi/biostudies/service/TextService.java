package uk.ac.ebi.biostudies.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
@PropertySource("classpath:normalised-text.properties")
public class TextService implements InitializingBean, DisposableBean {


    private Logger logger = LogManager.getLogger(TextService.class.getName());

    @Autowired
    Environment env;

    public String getNormalisedString(String string) {
        return env.containsProperty(string) ? env.getProperty(string)  : string;
    }

    @Override
    public void destroy() {

    }

    @Override
    public void afterPropertiesSet() {
        logger.debug("Initialising TextService");
    }
}
