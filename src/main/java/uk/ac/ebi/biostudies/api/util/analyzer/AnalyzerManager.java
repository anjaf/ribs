package uk.ac.ebi.biostudies.api.util.analyzer;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ehsan on 27/03/2017.
 */
@Service
public class AnalyzerManager {
    private Logger logger = LogManager.getLogger(AnalyzerManager.class.getName());


    private Map<String, Analyzer> perFieldAnalyzerMap= new HashMap<>();
    private PerFieldAnalyzerWrapper perFieldAnalyzerWrapper;
    private Map<String, String> expandableFields = new HashMap<>();

    public void init(Map<String, JsonNode> allFields){
        for(String key:allFields.keySet()){
            JsonNode curField = allFields.get(key);
            String analyzer = curField.get("analyzer").asText();
            String expand = curField.get("isExpand").asText();
            if(analyzer!=null && !analyzer.isEmpty()) {
                analyzer="uk.ac.ebi.biostudies.api.util.analyzer."+analyzer;
                Class<?> clazz = null;
                try {
                    clazz = Class.forName(analyzer);
                    Analyzer analyzerObj = (Analyzer) clazz.newInstance();
                    perFieldAnalyzerMap.put(key, analyzerObj);
                } catch (Exception e) {
                    logger.error("cant create analyzer with name {1}", analyzer, e);
                }
            }
            if(expand!=null && expand.equalsIgnoreCase("true"))
                expandableFields.put(key, key);
        }
        perFieldAnalyzerWrapper = new PerFieldAnalyzerWrapper(new AttributeFieldAnalyzer(), perFieldAnalyzerMap);
    }

    public PerFieldAnalyzerWrapper getPerFieldAnalyzerWrapper(){
        return perFieldAnalyzerWrapper;
    }

    public Map<String, String> getExpandableFields(){
        return expandableFields;
    }
}
