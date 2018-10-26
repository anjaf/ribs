package uk.ac.ebi.biostudies.api.util.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.jayway.jsonpath.ReadContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.ac.ebi.biostudies.api.util.Constants;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static uk.ac.ebi.biostudies.api.util.Constants.NA;

public class JPathListParser extends AbstractParser{
    private static final Logger LOGGER = LogManager.getLogger(JPathListParser.class.getName());

    @Override
    public String parse(Map<String, Object> valueMap, JsonNode submission, String accession, JsonNode fieldMetadataNode, ReadContext jsonPathContext) {
        Object result= NA;
        try {
            List resultData = jsonPathContext.read(fieldMetadataNode.get(Constants.IndexEntryAttributes.JSON_PATH).asText());
            switch (fieldMetadataNode.get(Constants.IndexEntryAttributes.FIELD_TYPE).asText()) {
                case Constants.IndexEntryAttributes.FieldTypeValues.FACET:
                    result =  String.join (Constants.Facets.DELIMITER, resultData);
                    break;
                case Constants.IndexEntryAttributes.FieldTypeValues.LONG:
                    result = resultData.stream().collect(Collectors.counting());
                    break;
                default:
                    result =  String.join (" ", resultData);
                    break;
            }

        } catch (Exception e) {
            if(valueMap.containsKey(Constants.Fields.TYPE) && valueMap.getOrDefault(Constants.Fields.TYPE, "").toString().equalsIgnoreCase("project"))
                return "";
            if(indexFieldKey.equalsIgnoreCase("author") || indexFieldKey.equalsIgnoreCase("orcid"))
                return "";
            LOGGER.error("problem in parsing field:{} in {}", indexFieldKey, accession);
        }
        valueMap.put(indexFieldKey, result);
        return result.toString();
    }
}
