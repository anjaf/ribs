package uk.ac.ebi.biostudies.api.util.parser;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import uk.ac.ebi.biostudies.api.util.Constants;
import uk.ac.ebi.biostudies.api.util.analyzer.AttributeFieldAnalyzer;
import uk.ac.ebi.biostudies.service.impl.IndexServiceImpl;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Scope("singleton")
public class ParserManager {

    private Map<String, AbstractParser>  parserPool;
    private Logger logger = LogManager.getLogger(ParserManager.class.getName());

    public void init(Map<String, JsonNode> allFields){
        if(parserPool==null)
            parserPool = new HashMap<>();

        for(String key:allFields.keySet()){
            JsonNode curField = allFields.get(key);
            String parser = curField.has(Constants.IndexEntryAttributes.PARSER) ? curField.get(Constants.IndexEntryAttributes.PARSER).asText() : null;
            String jsonFieldKey = curField.has(Constants.IndexEntryAttributes.JSON_FIELD_KEY) ? curField.get(Constants.IndexEntryAttributes.JSON_FIELD_KEY).asText(key) : key;
            Boolean toLowerCase = curField.has(Constants.IndexEntryAttributes.TO_LOWER_CASE) ? curField.get(Constants.IndexEntryAttributes.TO_LOWER_CASE).asBoolean(false) : false;

            if(parser==null || parser.isEmpty() || parser.equalsIgnoreCase("null")) {
                continue;
            }
            addParser(parser, jsonFieldKey, key, toLowerCase);
        }
        addParser("JPathListParser", "generalWithPathParser", "generalWithPathParser", false);
        addParser("SimpleAttributeParser", "generalWithoutPathParser", "generalWithoutPathParser", false);
    }

    private void addParser(String parser, String jsonFieldKey, String key, boolean toLowerCase){
        String parserClass =  this.getClass().getPackage().getName() + "." + parser;
        Class<?> clazz;
        try {
            clazz = Class.forName(parserClass);
            AbstractParser parserObj = (AbstractParser) clazz.newInstance();
            parserObj.setToLowerCase(toLowerCase);
            parserObj.setJsonFieldKey(jsonFieldKey);
            parserObj.setIndexFieldKey(key);
            parserPool.put(key, parserObj);
        } catch (Exception e) {
            logger.error("cant create Parser with name {}", parser, e);
        }
    }

    public Map<String, AbstractParser> getParserPool(){
        return parserPool;
    }
}
