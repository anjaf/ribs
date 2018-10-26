package uk.ac.ebi.biostudies.api.util.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.jayway.jsonpath.ReadContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.ac.ebi.biostudies.api.util.Constants;

import java.util.Map;

import static uk.ac.ebi.biostudies.api.util.Constants.NA;

public class JPathSimpleParser extends AbstractParser{
    private final static Logger LOGGER = LogManager.getLogger(JPathSimpleParser.class.getName());


    @Override
    public String parse(Map<String, Object> valueMap, JsonNode submission, String accession, JsonNode fieldMetadataNode, ReadContext jsonPathContext) {
        String result= NA;
        try {
            String resultData = jsonPathContext.read(fieldMetadataNode.get(Constants.IndexEntryAttributes.JSON_PATH).asText(), String.class);
            result = toLowerCase?resultData.toLowerCase():resultData;
        }catch (Exception ex){
            LOGGER.error("problem in parsing accession:{} field:{}", accession, getJsonFieldKey(), ex);
        }
        valueMap.put(indexFieldKey, result);
        return result;
    }
}
