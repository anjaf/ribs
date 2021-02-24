package uk.ac.ebi.biostudies.config;

import net.jawr.web.servlet.JawrSpringController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class JawrConfig {

    private static final Logger LOGGER = LogManager.getLogger(JawrConfig.class.getName());

    @Autowired
    private JawrSpringController jawrJsController;
    @Autowired
    private JawrSpringController jawrCssController;

    private final static Properties JAWRCONFIG = null;

    @Bean
    public HandlerMapping jawrHandlerMapping() {
        SimpleUrlHandlerMapping handlerMapping = new SimpleUrlHandlerMapping();
        handlerMapping.setOrder(-2147483648);
        Map<String, Object> urlMap = new HashMap();
        urlMap.put("**/*.css", this.jawrCssController);
        urlMap.put("**/*.js", this.jawrJsController);
        handlerMapping.setUrlMap(urlMap);
        return handlerMapping;
    }

    static Properties loadJawrEnvironmentalConfig(Environment environment) {
        if (JAWRCONFIG != null)
            return JAWRCONFIG;
        Resource resource = new ClassPathResource("/jawr.properties");
        Properties JAWRCONFIG = null;
        try {
            JAWRCONFIG = PropertiesLoaderUtils.loadProperties(resource);
            for (Object key : JAWRCONFIG.keySet()) {
                if (environment.containsProperty((String) key)) {
                    JAWRCONFIG.put(key, environment.getProperty((String) key));
                }
            }
        } catch (Exception exception) {
            LOGGER.error("problem in loading jawr config from environment", exception);
        }
        return JAWRCONFIG;
    }

}
