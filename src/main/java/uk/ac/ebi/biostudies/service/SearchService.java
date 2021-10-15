package uk.ac.ebi.biostudies.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.lucene.document.Document;
import org.springframework.core.io.InputStreamResource;

import java.io.IOException;

/**
 * Created by ehsan on 27/02/2017.
 */
public interface SearchService {
    String search(String query, JsonNode selectedFacets, String prjName, int page, int pageSize, String sortBy, String sortOrder);
    String getKeywords(String query, String field, Integer limit);
    String getEfoTree(String query);
    String getFieldStats() throws Exception;
    void clearStatsCache();
    InputStreamResource getStudyAsStream(String accession, String relativePath, boolean b) throws IOException;
    ObjectNode getSimilarStudies(String accession, String secretKey) throws Exception;
    Document getSubmissionDocumentByAccession(String accession, String secretKey) throws SubmissionNotAccessibleException;
    Document getFileDocumentByAccessionAndPath(String accession, String path, String secretKey) throws SubmissionNotAccessibleException;
    boolean isDocumentPresent(String accession);
    String getLatestStudies() throws Exception;
}

