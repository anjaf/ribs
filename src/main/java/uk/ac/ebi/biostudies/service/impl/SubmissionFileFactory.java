package uk.ac.ebi.biostudies.service.impl;


import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.ac.ebi.biostudies.api.util.Constants;
import uk.ac.ebi.biostudies.config.IndexConfig;

@Component
public class SubmissionFileFactory {

    @Autowired
    private IndexConfig indexConfig;

    @Autowired
    private FireService fireService;

    public SubmissionFile createSubmissionFile(JsonNode fileNode, String accession, String relativePath) {
        SubmissionFile submissionFile;
        //TODO: Remove check for PATH
        String key = fileNode.has(Constants.File.FILE_PATH) ? Constants.File.FILE_PATH : Constants.File.PATH;
        String filePath = fileNode.get(key).textValue();
        if (fileNode.get(Constants.File.EXT_TYPE).textValue().equalsIgnoreCase("fireFile")) {
            submissionFile = new FireSubmissionFile(filePath, fireService);
        } else {
            submissionFile = new NFSSubmissionFile(indexConfig.getFileRootDir() + '/' + relativePath + "/Files/" + filePath);
        }
        return submissionFile;
    }
}

