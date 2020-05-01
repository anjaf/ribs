package uk.ac.ebi.biostudies.api.util.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.jayway.jsonpath.ReadContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.ac.ebi.biostudies.api.util.Constants;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static uk.ac.ebi.biostudies.api.util.Constants.NA;

public class SimpleAttributeParser extends AbstractParser {
    private final static Logger LOGGER = LogManager.getLogger(SimpleAttributeParser.class.getName());

    private static String jpath = "$.section.attributes[?(@.name=~ /%s/i)].value";
    @Override
    public String parse(Map<String, Object> valueMap, JsonNode submission, ReadContext jsonPathContext) {
        Object result= NA;
        String indexKey = indexEntry.get(Constants.IndexEntryAttributes.NAME).asText();
        try {
            String title = StringUtils.replace(indexEntry.get(Constants.Fields.TITLE).asText(), "/", "\\/");
            String newJPath = String.format(jpath, title);
            List resultData = jsonPathContext.read(newJPath);
            switch (indexEntry.get(Constants.IndexEntryAttributes.FIELD_TYPE).asText()) {
                case Constants.IndexEntryAttributes.FieldTypeValues.FACET:
                    result =  String.join(Constants.Facets.DELIMITER, resultData);
                    break;
                case Constants.IndexEntryAttributes.FieldTypeValues.LONG:
                    result =  resultData.size()==1 ? Long.parseLong(resultData.get(0).toString()) :  resultData.stream().collect(Collectors.counting());
                    break;
                default:
                    result =  String.join (" ", resultData);
                    break;
            }

        } catch (Exception ex) {
            LOGGER.debug("problem in parsing facet: {} in {}", indexKey, valueMap.toString(), ex);
        }
        valueMap.put(indexKey, result);
        return result.toString();
    }
}
