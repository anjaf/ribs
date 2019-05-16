package uk.ac.ebi.biostudies.api.util.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.jayway.jsonpath.ReadContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.ac.ebi.biostudies.api.util.Constants;

import java.util.Map;

public class EUToxRiskDataTypeParser extends AbstractParser{
    private static final Logger LOGGER = LogManager.getLogger(EUToxRiskDataTypeParser.class.getName());

    @Override
    public String parse(Map<String, Object> valueMap, JsonNode submission, ReadContext jsonPathContext) {
        String indexKey = indexEntry.get(Constants.IndexEntryAttributes.NAME).asText();
        boolean hasQMRFId = !jsonPathContext.read("$.section.attributes[?(@.name==\"QMRF-ID\")].value").toString().equalsIgnoreCase("[]");
        Object result = hasQMRFId ? "in silico" : "in vitro";
        valueMap.put(indexKey, result);
        return result.toString();
    }
}
