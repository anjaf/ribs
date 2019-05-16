package uk.ac.ebi.biostudies.api.util.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.jayway.jsonpath.ReadContext;
import net.minidev.json.JSONArray;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.xpath.operations.Bool;
import uk.ac.ebi.biostudies.api.util.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static uk.ac.ebi.biostudies.api.util.Constants.NA;

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
