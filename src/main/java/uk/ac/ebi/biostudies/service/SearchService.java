package uk.ac.ebi.biostudies.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.InputStreamResource;

import java.io.IOException;
import java.util.Map;

/**
 * Created by ehsan on 27/02/2017.
 */
public interface SearchService {
    String search(String query, JsonNode selectedFacets, String prjName, int page, int pageSize, String sortBy, String sortOrder);
    String getKeywords(String query, String field, Integer limit);
    String getEfoTree(String query);
    String getFieldStats() throws Exception;
    void clearStatsCache();
    String getStudyAsStream(String accession, String relativePath, boolean b) throws IOException;
    ObjectNode getSimilarStudies(String accession, String secretKey) throws Exception;
    Document getDocumentByAccession(String accession, String secretKey) throws SubmissionNotAccessibleException;
    boolean isDocumentPresent(String accession);
    String getLatestStudies() throws Exception;
}

