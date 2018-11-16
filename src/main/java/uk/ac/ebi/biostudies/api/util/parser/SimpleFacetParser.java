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

public class SimpleFacetParser extends AbstractParser {
    private final static Logger LOGGER = LogManager.getLogger(JPathSimpleParser.class.getName());

    private static String jpath = "$.section.attributes[?(@.name=~ /%s/i)].value";
    @Override
    public String parse(Map<String, Object> valueMap, JsonNode submission, String accession, JsonNode fieldMetadataNode, ReadContext jsonPathContext) {
        Object result= NA;
        try {
            String title = StringUtils.replace(fieldMetadataNode.get(Constants.Fields.TITLE).asText(), "/", "\\/");
            String newJPath = String.format(jpath, title);
            List resultData = jsonPathContext.read(newJPath);
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

        } catch (Exception ex) {
            LOGGER.debug("problem in parsing facet: {} in accession: {}", indexFieldKey, accession, ex);
        }
        valueMap.put(indexFieldKey, result);
        return result.toString();
    }
}