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

public class FileTypeParser extends AbstractParser{
    private static final Logger LOGGER = LogManager.getLogger(FileTypeParser.class.getName());

    @Override
    public String parse(Map<String, Object> valueMap, JsonNode submission, String accession, JsonNode fieldMetadataNode, ReadContext jsonPathContext) {
        String result=NA;
        try {
            List<String> resultData = jsonPathContext.read(fieldMetadataNode.get(Constants.IndexEntryAttributes.JSON_PATH).asText());
            result = resultData.stream()
                    .map(s -> {
                        if (s == null) return NA;
                        int k = s.lastIndexOf(".");
                        return k >= 0 ? s.substring(s.lastIndexOf(".") + 1) : NA;
                    }).collect(Collectors.joining(Constants.Facets.DELIMITER));
            valueMap.put(getIndexFieldKey(), result);
        }
        catch (Exception ex){
            LOGGER.error("problem in parsing FILE_TYPE in {}", accession, ex);
        }
        return result;
    }
}
