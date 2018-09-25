package uk.ac.ebi.biostudies.service;

import org.springframework.scheduling.annotation.Async;

import java.io.IOException;
import java.util.Dictionary;
import java.util.concurrent.BlockingQueue;

/**
 * Created by ehsan on 27/02/2017.
 */
public interface IndexService {
    @Async
    public void indexAll(String fileName, boolean removeFileDocuments);

    public void deleteDoc(String accession) throws Exception;

    public void clearIndex(boolean commit) throws IOException;

    public void copySourceFile(String jsonFileName) throws IOException;

    public BlockingQueue<String> getIndexFileQueue();

    public void processFileForIndexing();

}
