package uk.ac.ebi.biostudies.service;


import com.fasterxml.jackson.databind.JsonNode;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;

import java.io.IOException;
import java.util.Map;

/**
 * Created by ehsan on 27/02/2017.
 */
public interface SearchService {
    String search(String query, int page, int pageSize);
    String getDetailFile(String accessionNumber) throws IOException;

    /**
     *
     * @param queryInfo the field's names in the query that you want to be expand, if you do not add the field's name, It will not be expand
     * @param originalQuery the original user query
     * @param synonymBooleanBuilder list of synonyms that are added to the original query
     * @param efoBooleanBuilder list of EFOs that are added to the original query
     * @return
     */
    Query expandQuery(Map<String, String> queryInfo, Query originalQuery, BooleanQuery.Builder synonymBooleanBuilder, BooleanQuery.Builder efoBooleanBuilder);

    /**
     *
     * @param originalQuery
     * @param synonymQuery
     * @param efoQuery
     * @param fieldName the field name that you want apply fragmentation
     * @param text
     * @param fragmentOnly true:breaks text up into same-size fragments false:This is useful for highlighting the entire content of a document or field
     * @return
     */
    String highlightText(Query originalQuery, Query synonymQuery, Query efoQuery, String fieldName, String text, boolean fragmentOnly);

    String getKeywords(String query, String field, Integer limit);

    String getEfoWords(String query, Integer limit);

    Query applyFacets(Query query, JsonNode facets);

    String applySearchOnQuery(Query query, int page, int pageSize);
}

