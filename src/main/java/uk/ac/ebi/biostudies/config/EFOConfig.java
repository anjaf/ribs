package uk.ac.ebi.biostudies.config;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ehsan on 02/03/2017.
 */


@Configuration
@PropertySource("classpath:efo.properties")
public class EFOConfig implements InitializingBean, DisposableBean {

    @Value("${efo.stopWords}")
    private String stopWords;

    @Value("${efo.index.location}")
    private String indexLocation;

    @Value("${bs.efo.synonyms}")
    private String synonymFilename;

    @Value("${bs.efo.ignoreList}")
    private String ignoreListFilename;

    @Value("${bs.efo.owl}")
    private String owlFilename;

    @Value("${bs.efo.update.source}")
    private String url;

    @Value("${bs.efo.source}")
    private String localOwlFilename;

//    @Value("${bs.efo.location}")
//    private String efoLocation;



    private Set<String> stopWordsSet = new HashSet<>();

    @Override
    public void afterPropertiesSet() {
        String[] words = stopWords.split("\\s*,\\s*");
        stopWordsSet.addAll(Arrays.asList(words));
    }

    public String getUrl(){
        return url;
    }

    public String getIndexLocation(){
        return indexLocation;
    }

    public Set<String> getStopWordsSet(){
        return stopWordsSet;
    }

    public String getSynonymFilename(){
        return synonymFilename;
    }

    public String getIgnoreListFilename(){
        return ignoreListFilename;
    }

    public String getLocalOwlFilename(){
        return localOwlFilename;
    }

    public String getOwlFilename(){
        return owlFilename;
    }


    @Override
    public void destroy() {

    }

}
