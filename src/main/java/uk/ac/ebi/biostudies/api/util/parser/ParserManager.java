package uk.ac.ebi.biostudies.api.util.parser;

import com.fasterxml.jackson.databind.JsonNode;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParserManager {
    private Map<String, Object> valueMap;
    private Map<String, Object> parserContext;
    private List<AbstractParser> allParsers;
    private Map<String, AbstractParser>  parserPool;
    @PostConstruct
    void init(){
        if(parserPool==null)
            parserPool = new HashMap<>();
        for(AbstractParser stdParser:allParsers){
            parserPool.put(stdParser.getIndexFieldKey(), stdParser);
        }
    }

    public Map<String, Object> getValueMap() {
        return valueMap;
    }

    public void setValueMap(Map<String, Object> valueMap) {
        this.valueMap = valueMap;
    }

    public Map<String, Object> getParserContext() {
        return parserContext;
    }

    public void setParserContext(Map<String, Object> parserContext) {
        this.parserContext = parserContext;
    }

    public List<AbstractParser> getAllParsers() {
        return allParsers;
    }

    public void setAllParsers(List<AbstractParser> allParsers) {
        this.allParsers = allParsers;
    }

    public Map<String, AbstractParser> getParserPool(){
        return parserPool;
    }
}
