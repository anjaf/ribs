package uk.ac.ebi.biostudies.api.util.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.jayway.jsonpath.ReadContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class SimpleStudyParser extends AbstractParser{
    private final static Logger LOGGER = LogManager.getLogger(SimpleStudyParser.class.getName());

    @Override
    public String parse(Map<String, Object> valueMap, JsonNode submission, String accession, JsonNode fieldMetadataNode, ReadContext jsonPathContext){
        try {
            if(submission.get(jsonFieldKey)==null)
                return "NA";
            String value = toLowerCase ? submission.findValue(jsonFieldKey).textValue().toLowerCase() : submission.findValue(jsonFieldKey).textValue();
            valueMap.put(indexFieldKey, value);
            return value;
        }
        catch (Throwable ex){
            LOGGER.error("problem in parsing field: {} in submission: ", jsonFieldKey, accession, ex);
        }
        return "NA";
    }



}
