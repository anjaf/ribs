package uk.ac.ebi.biostudies.api.util.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.jayway.jsonpath.ReadContext;
import net.minidev.json.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.ac.ebi.biostudies.api.util.Constants;

import java.util.Map;
import java.util.stream.Collectors;

import static uk.ac.ebi.biostudies.api.util.Constants.NA;

public class FileTypeParser extends AbstractParser{
    private static final Logger LOGGER = LogManager.getLogger(FileTypeParser.class.getName());

    @Override
    public String parse(Map<String, Object> valueMap, JsonNode submission, ReadContext jsonPathContext) {
        String result=NA;
        try {
            String indexKey = indexEntry.get(Constants.IndexEntryAttributes.NAME).asText();
            JSONArray resultData =  jsonPathContext.read(indexEntry.get(Constants.IndexEntryAttributes.JSON_PATH).asText());
            result = resultData.stream()
                    .map(jnode -> {
                        Map node = (Map)jnode;
                        if (node==null) return NA;
                        String s = (String)(node.containsKey("path") && node.get("path")!=null ? node.get("path"): node.get("name"));
                        if ( StringUtils.isEmpty(s)) return NA;
                        int k = s.lastIndexOf(".");
                        return k >= 0 ? s.substring(s.lastIndexOf(".") + 1) : NA;
                    }).collect(Collectors.joining(Constants.Facets.DELIMITER));
            valueMap.put(indexKey, result);
        }
        catch (Exception ex){
            LOGGER.error("problem in parsing FILE_TYPE in {}", indexEntry.get(Constants.IndexEntryAttributes.NAME), ex);
        }
        return result;
    }
}
