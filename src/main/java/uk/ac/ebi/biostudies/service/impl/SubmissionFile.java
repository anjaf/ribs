package uk.ac.ebi.biostudies.service.impl;


import org.springframework.core.io.InputStreamResource;

import java.io.IOException;
import java.util.Set;

public interface SubmissionFile {
    Set<String> sectionsToFilter = Set.of(new String[]{"author", "organisation", "organization"}) ;
    InputStreamResource getInputStreamResource() throws IOException;
}

