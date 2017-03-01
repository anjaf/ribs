package uk.com.ebi.biostudy.service;

import org.springframework.scheduling.annotation.Async;

/**
 * Created by ehsan on 27/02/2017.
 */
public interface IndexService {
    @Async
    public void indexAll();
    @Async
    public void indexSingleDoc();
    @Async
    public void deleteDoc();
    @Async
    public void clearIndex();
}
