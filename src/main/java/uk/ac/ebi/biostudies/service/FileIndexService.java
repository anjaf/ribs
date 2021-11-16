package uk.ac.ebi.biostudies.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.lucene.index.IndexWriter;

import java.io.IOException;
import java.util.Map;

public interface FileIndexService {
    void indexSubmissionFiles(Map<String, Object> valueMap, JsonNode submissionJson, IndexWriter writer, boolean removeFileDocuments) throws IOException ;
}
