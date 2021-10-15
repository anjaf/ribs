package uk.ac.ebi.biostudies.service.impl;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.lucene.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.ac.ebi.biostudies.api.util.Constants;
import uk.ac.ebi.biostudies.config.IndexConfig;

import java.io.IOException;

@Component
public class SubmissionReaderFactory {

    @Autowired
    private IndexConfig indexConfig;

    @Autowired
    private FireService fireService;

    public SubmissionFile createSubmissionFileReader(Document document, String secretKey) throws IOException {
        String accession = document.get(Constants.Fields.ACCESSION);
        String relativePath = document.get(Constants.Fields.RELATIVE_PATH);
        String pageTabJson = document.get(Constants.File.PAGETAB_JSON);
        boolean anonymise = secretKey != null;

        SubmissionFile submissionFile;
        JsonNode json = new ObjectMapper().readTree(pageTabJson).get(0);
        if (json.get(Constants.File.EXT_TYPE).asText().equalsIgnoreCase(Constants.File.FileExtType.NFS_FILE)) {
            submissionFile = new NFSSubmissionFile(indexConfig.getFileRootDir() + '/' + relativePath + "/"+ json.get(Constants.File.PATH).asText());
        } else {
            submissionFile = new FireSubmissionFile(json.get(Constants.File.FILE_PATH).asText(), fireService);
        }
        return submissionFile;
    }
}

