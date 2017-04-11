package uk.ac.ebi.biostudies.service;

import org.springframework.scheduling.annotation.Async;

import java.io.IOException;

/**
 * Created by ehsan on 27/02/2017.
 */
public interface IndexService {
    @Async
    public void indexAll(String fileName);

    public void deleteDoc(String accession) throws Exception;

    public void clearIndex(boolean commit) throws IOException;

    public void updateFromJSONFile(String jsonFileName);

}
