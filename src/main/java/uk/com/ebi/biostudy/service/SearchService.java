package uk.com.ebi.biostudy.service;


import java.io.IOException;

/**
 * Created by ehsan on 27/02/2017.
 */
public interface SearchService {
    public String search(String query, int page, int pageSize);
    public String getDetailFile(String accessionNumber) throws IOException;
}
