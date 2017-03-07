package uk.ac.ebi.biostudies.lucene.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ehsan on 02/03/2017.
 */


@Configuration
@PropertySource("classpath:efo.properties")
public class EFOConfig {

    @Value("${efo.stopWords}")
    private String stopWords;

    @Value("${efo.index.location}")
    private String efoIndexLocation;

    @Value("${bs.efo.synonyms}")
    private String efoSynonyms;

    @Value("${bs.efo.ignoreList}")
    private String ignoreList;



    private Set<String> stopWordsSet = new HashSet<>();

    @PostConstruct
    private void init(){
        String[] words = stopWords.split("\\s*,\\s*");
        stopWordsSet.addAll(Arrays.asList(words));
    }

    public String getEfoIndexLocation(){
        return efoIndexLocation;
    }

    public Set<String> getStopWordsSet(){
        return stopWordsSet;
    }

    public String getEfoSynonyms(){
        return efoSynonyms;
    }

    public String getIgnoreList(){
        return ignoreList;
    }


}
