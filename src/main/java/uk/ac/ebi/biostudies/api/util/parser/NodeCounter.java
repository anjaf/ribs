package uk.ac.ebi.biostudies.api.util.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.jayway.jsonpath.ReadContext;
import uk.ac.ebi.biostudies.api.util.Constants;

import java.util.List;
import java.util.Map;

public class NodeCounter extends AbstractParser {
    @Override
    public String parse(Map<String, Object> valueMap, JsonNode submission, String accession, JsonNode fieldMetadataNode, ReadContext jsonPathContext) {
        String indexKey = fieldMetadataNode.get(Constants.IndexEntryAttributes.NAME).asText();
        List result = jsonPathContext.read(fieldMetadataNode.get(Constants.IndexEntryAttributes.JSON_PATH).asText());
        long length = 0;
        length = result!=null?result.size():length;
        valueMap.put(indexKey, length);
        return String.valueOf(length);
    }
}
