package uk.ac.ebi.biostudies.api.util.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.jayway.jsonpath.ReadContext;

import java.util.Map;

public abstract class AbstractParser {
    protected String indexFieldKey;
    protected String jsonFieldKey;
    protected boolean toLowerCase = false;

    public abstract String parse(Map<String, Object> valueMap, JsonNode submission, String accession, JsonNode fieldMetadataNode, ReadContext jsonPathContext);

    public String getIndexFieldKey() {
        return indexFieldKey;
    }

    public void setIndexFieldKey(String indexFieldKey) {
        this.indexFieldKey = indexFieldKey;
    }

    public String getJsonFieldKey() {
        return jsonFieldKey;
    }

    public void setJsonFieldKey(String jsonFieldKey) {
        this.jsonFieldKey = jsonFieldKey;
    }

    public boolean isToLowerCase() {
        return toLowerCase;
    }

    public void setToLowerCase(boolean toLowerCase) {
        this.toLowerCase = toLowerCase;
    }

}
