package uk.ac.ebi.biostudies.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.InputStreamResource;

import java.io.IOException;


public class FireSubmissionFile implements SubmissionFile {

    private final Logger logger = LogManager.getLogger(FireSubmissionFile.class.getName());
    private String path;
    private FireService fireService;


    public FireSubmissionFile(String path, FireService fireService) {
        this.path = path.startsWith("/") ? path.substring(1) : path ;
        this.fireService = fireService;
    }

    @Override
    public InputStreamResource getInputStreamResource() throws IOException {
        return  fireService.getFireObjectInputStream(path);
    }
}
