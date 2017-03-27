package uk.ac.ebi.biostudies.filters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.ac.ebi.biostudies.efo.EFOQueryExpander;
import uk.ac.ebi.biostudies.efo.ExperimentTextAnalyzer;

import java.util.Map;

/**
 * Created by ehsan on 14/03/2017.
 */

@Component
public class ExpandFilter implements QueryFilter{

    private Logger logger = LogManager.getLogger(ExpandFilter.class.getName());


    @Autowired
    EFOQueryExpander efoQueryExpander;

    @Override
    public void applyFilter(BooleanQuery.Builder queryBuilder, Map<String, Object> parameters) throws Throwable {
        String query = (String)parameters.get("query");
        Query origQuery = null;
        if(query==null || query.isEmpty())
            query = "*:*";
        QueryParser parser = new QueryParser("content", new ExperimentTextAnalyzer());
        try {
            origQuery = parser.parse(query);
            BooleanQuery.Builder synonym = new BooleanQuery.Builder();
            BooleanQuery.Builder efo = new BooleanQuery.Builder();
            Query result = efoQueryExpander.expand(null, origQuery, synonym, efo);
            queryBuilder.add(result, BooleanClause.Occur.MUST);
        } catch (Exception e) {
            logger.debug(e);
            throw e;
        }


    }
}
