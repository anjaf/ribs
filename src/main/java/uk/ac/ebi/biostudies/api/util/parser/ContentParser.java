package uk.ac.ebi.biostudies.api.util.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.jayway.jsonpath.ReadContext;
import uk.ac.ebi.biostudies.api.util.Constants;

import java.util.Map;
import java.util.stream.Collectors;

public class ContentParser extends AbstractParser {
    @Override
    public String parse(Map<String, Object> valueMap, JsonNode submission, String accession, JsonNode fieldMetadataNode, ReadContext jsonPathContext) {
        StringBuilder content = new StringBuilder(String.join(" ", accession));
        content.append(" ");
        if (accession.startsWith("S-EPMC")) {// hack to make sure we are indexing PMC accessions in full text
            content.append( accession.substring(3) ).append(" ");
        }
        content.append(String.join(" ", submission.get("section").findValuesAsText("value")));
        content.append(" ");
        content.append(submission.findValues("files").stream().map(jsonNode -> jsonNode.findValuesAsText("path").stream().collect(Collectors.joining(" "))).collect(Collectors.joining(" ")));
        content.append(" ");
        content.append(submission.findValues("links").stream().map(jsonNode -> jsonNode.findValuesAsText("url").stream().collect(Collectors.joining(" "))).collect(Collectors.joining(" ")));
        valueMap.put( Constants.Fields.CONTENT, content.toString());
        return "";
    }
}
