package uk.ac.ebi.biostudies.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.InputStreamResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;

public class NFSSubmissionFile implements SubmissionFile {

    private final Logger logger = LogManager.getLogger(NFSSubmissionFile.class.getName());
    private final String path;

    public NFSSubmissionFile(String path) {
        super();
        this.path = path;
    }

    @Override
    public InputStreamResource getInputStreamResource() throws IOException {
        File file = Paths.get(path).toFile();
        return new InputStreamResource(new FileInputStream(file));
    }
}
