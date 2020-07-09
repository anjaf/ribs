package uk.ac.ebi.biostudies.api.util.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.jayway.jsonpath.JsonPathException;
import com.jayway.jsonpath.ReadContext;
import net.minidev.json.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.ac.ebi.biostudies.api.util.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static uk.ac.ebi.biostudies.api.util.Constants.NA;

public class JPathListParser extends AbstractParser{
    private static final Logger logger = LogManager.getLogger(JPathListParser.class.getName());

    @Override
    public String parse(Map<String, Object> valueMap, JsonNode submission, ReadContext jsonPathContext) {
        Object result= NA;
        String indexKey = indexEntry.get(Constants.IndexEntryAttributes.NAME).asText();
        String fieldType="";
        try {
            String jsonPath = indexEntry.get(Constants.IndexEntryAttributes.JSON_PATH).asText();
            List resultData = new ArrayList<>();
            for (String jp: jsonPath.split(" OR ")) {
                try {
                    Object obj = jsonPathContext.read(jp)  ;
                    if (obj instanceof JSONArray) {
                        resultData.addAll((JSONArray)obj);
                    } else {
                        resultData.add(obj);
                    }
                } catch (JsonPathException ex) {
                    logger.debug("skipping {}", jp);
                }
            }

            fieldType = indexEntry.get(Constants.IndexEntryAttributes.FIELD_TYPE).asText();
            switch (fieldType) {
                case Constants.IndexEntryAttributes.FieldTypeValues.FACET:
                    result =  String.join (Constants.Facets.DELIMITER, resultData);
                    break;
                case Constants.IndexEntryAttributes.FieldTypeValues.LONG:
                    result = resultData.stream().mapToLong( v -> Long.parseLong(v.toString()) ).sum();
                    break;
                default:
                    result =  String.join (" ", resultData);
                    break;
            }

            if (indexEntry.has(Constants.IndexEntryAttributes.TO_LOWER_CASE)
                    && indexEntry.get(Constants.IndexEntryAttributes.TO_LOWER_CASE).asBoolean(false)) {
                result = result.toString().toLowerCase();
            }


        } catch (Exception e) {
            if(valueMap.containsKey(Constants.Fields.TYPE) && valueMap.getOrDefault(Constants.Fields.TYPE, "").toString().equalsIgnoreCase("project"))
                return "";
            if(indexKey.equalsIgnoreCase("author") || indexKey.equalsIgnoreCase("orcid"))
                return "";
            logger.error("problem in parsing field:{} in {}", indexEntry, valueMap, e);
        }
        valueMap.put(indexKey, result);
        return result.toString();
    }
}
