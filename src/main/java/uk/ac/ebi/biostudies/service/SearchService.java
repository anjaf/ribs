package uk.ac.ebi.biostudies.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;

import java.io.IOException;
import java.util.Map;

/**
 * Created by ehsan on 27/02/2017.
 */
public interface SearchService {
    String search(String query, JsonNode selectedFacets, String prjName, int page, int pagesize, String sortBy, String sortOrder);
    String getDetailFile(String accessionNumber) throws IOException;

    /**
     *
     * @param originalQuery the original user query
     * @param synonymBooleanBuilder list of synonyms that are added to the original query
     * @param efoBooleanBuilder list of EFOs that are added to the original query
     * @return the expanded query
     */
    Query expandQuery(Query originalQuery, BooleanQuery.Builder synonymBooleanBuilder, BooleanQuery.Builder efoBooleanBuilder);

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

    Query applyFacets(Query query, JsonNode facets, String prjName);

    ObjectNode applySearchOnQuery(Query query, Query synonymQuery, Query efoQuery, int page, int pageSize, String sortBy, String sortOrder) throws ParseException;

    boolean isAccessible(String accession);
}

