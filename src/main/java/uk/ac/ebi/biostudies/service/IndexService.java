package uk.ac.ebi.biostudies.service;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.annotation.Async;

import java.io.IOException;
import java.io.InputStream;
import java.util.Dictionary;
import java.util.concurrent.BlockingQueue;

/**
 * Created by ehsan on 27/02/2017.
 */
public interface IndexService extends InitializingBean, DisposableBean {
    @Async
    void indexAll(InputStream inputStream, boolean removeFileDocuments) throws IOException;

    @Async
    void indexOne(InputStream inputStream, boolean removeFileDocuments) throws IOException;

    void deleteDoc(String accession) throws Exception;

    void clearIndex(boolean commit) throws IOException;

    BlockingQueue<String> getIndexFileQueue();

    void processFileForIndexing();

}
