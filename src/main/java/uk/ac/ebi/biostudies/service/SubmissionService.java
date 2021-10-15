package uk.ac.ebi.biostudies.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.lucene.document.Document;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Set;

@Service
public interface SubmissionService {
    Set<String> sectionsToFilter = Set.of(new String[]{"author", "organisation", "organization"}) ;
    InputStreamResource getStudyAsStream(Document document, String secretKey) throws IOException;
    ObjectNode getStudyInfo(String accession, String secretKey) throws SubmissionNotAccessibleException;
}
