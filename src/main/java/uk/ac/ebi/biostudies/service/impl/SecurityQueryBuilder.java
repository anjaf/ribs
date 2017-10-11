package uk.ac.ebi.biostudies.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.springframework.stereotype.Service;
import uk.ac.ebi.biostudies.api.util.Constants;
import uk.ac.ebi.biostudies.api.util.analyzer.AccessFieldAnalyzer;
import uk.ac.ebi.biostudies.auth.Session;
import uk.ac.ebi.biostudies.auth.User;

/**
 * Created by ehsan on 14/03/2017.
 */
@Service
public class SecurityQueryBuilder {

    private Logger logger = LogManager.getLogger(SecurityQueryBuilder.class.getName());

    public Query applySecurity(Query originalQuery) throws Throwable {
        QueryParser queryParser = new QueryParser(Constants.ACCESS, new AccessFieldAnalyzer());
        queryParser.setSplitOnWhitespace(true);
        BooleanQuery.Builder queryBilder = new BooleanQuery.Builder();
        StringBuilder securityClause= new StringBuilder();
        User currentUser = Session.getCurrentUser();
        if(currentUser!=null && currentUser.isSuperUser())
            return originalQuery;
        int counter = 0;
        if(currentUser!=null && currentUser.getAllow()!=null && currentUser.getAllow().length>0)
            for(String allow:currentUser.getAllow()){
                securityClause.append(allow.toLowerCase());
                if(++counter<currentUser.getAllow().length)// check to find out is the length correct or not
                    securityClause.append(" OR ");
            }
        else
            securityClause.append("public");

        Query query = queryParser.parse(securityClause.toString());
        queryBilder.add(query, BooleanClause.Occur.MUST);
        counter = 0;
        securityClause = new StringBuilder();
        if(currentUser!=null && currentUser.getDeny()!=null && currentUser.getDeny().length>0) {
            for (String deny : currentUser.getDeny()) {
                securityClause.append(deny);
                if (++counter < currentUser.getDeny().length)// check to find out is the length correct or not
                    securityClause.append(" OR ");
            }
            query = queryParser.parse(securityClause.toString());
            queryBilder.add(query, BooleanClause.Occur.MUST_NOT);
        }
        logger.debug("security query: {}", queryBilder.build().toString());
        queryBilder.add(originalQuery, BooleanClause.Occur.MUST);
        return queryBilder.build();
    }
}
