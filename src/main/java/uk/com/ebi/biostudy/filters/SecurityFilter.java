package uk.com.ebi.biostudy.filters;

import org.apache.lucene.search.BooleanQuery;

import java.util.Map;

/**
 * Created by ehsan on 14/03/2017.
 */
public class SecurityFilter implements QueryFilter{

    @Override
    public void applyFilter(BooleanQuery.Builder queryBilder, Map<String, Object> parameters) throws Throwable {

    }
}
