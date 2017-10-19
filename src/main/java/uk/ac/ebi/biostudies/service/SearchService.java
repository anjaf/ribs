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
    String search(String query, JsonNode selectedFacets, String prjName, int page, int pageSize, String sortBy, String sortOrder);
    String getDetailFile(String accessionNumber) throws IOException;
    String getKeywords(String query, String field, Integer limit);
    String getEfoTree(String query);
    boolean isAccessible(String accession);
    boolean isAccessible(String accession, String seckey);
}

