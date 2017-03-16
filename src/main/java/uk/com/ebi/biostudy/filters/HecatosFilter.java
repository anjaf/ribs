package uk.com.ebi.biostudy.filters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import uk.ac.ebi.biostudies.api.BioStudiesField;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * Created by ehsan on 14/03/2017.
 */
public class HecatosFilter implements QueryFilter {
    private Logger logger = LogManager.getLogger(HecatosFilter.class.getName());


    Query hecatosQuery;

    @PostConstruct
    void init(){
        String queryString = "content:hecatos";
        QueryParser parser = new QueryParser(BioStudiesField.CONTENT.toString(), new SimpleAnalyzer());
        try {
            hecatosQuery = parser.parse(queryString);
        } catch (ParseException e) {
            logger.error(e);
        }
    }

    @Override
    public void applyFilter(BooleanQuery.Builder queryBilder, Map<String, Object> parameters) throws Throwable {
        queryBilder.add(hecatosQuery, BooleanClause.Occur.MUST);
    }
}
