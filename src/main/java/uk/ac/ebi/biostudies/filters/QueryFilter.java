package uk.ac.ebi.biostudies.filters;

import org.apache.lucene.search.BooleanQuery;

import java.util.Map;

/**
 * Created by ehsan on 14/03/2017.
 */
public interface QueryFilter {
    public void applyFilter(BooleanQuery.Builder queryBilder, Map<String, Object> parameters) throws Throwable;
}
