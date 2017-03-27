package uk.ac.ebi.biostudies.filters;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.springframework.stereotype.Service;
import uk.ac.ebi.biostudies.api.BioStudiesField;
import uk.ac.ebi.biostudies.auth.Session;
import uk.ac.ebi.biostudies.auth.User;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * Created by ehsan on 14/03/2017.
 */
@Service
public class SecurityFilter implements QueryFilter{

    private QueryParser queryParser;
    @PostConstruct
    void init(){
        queryParser = new QueryParser(BioStudiesField.ACCESS.toString(), new StandardAnalyzer());
    }
    @Override
    public void applyFilter(BooleanQuery.Builder queryBilder, Map<String, Object> parameters) throws Throwable {
        StringBuilder securityClause= new StringBuilder();
        User currentUser = Session.getCurrentUser();
        int counter = 0;
        if(currentUser!=null && currentUser.getAllow()!=null)
            for(String allow:currentUser.getAllow()){
                securityClause.append(allow);
                if(counter++<currentUser.getAllow().length)// check to find out is the length correct or not
                    securityClause.append(" OR ");
            }
        else
            securityClause.append("public");

        Query query = queryParser.parse(securityClause.toString());
        queryBilder.add(query, BooleanClause.Occur.MUST);
        counter = 0;
        securityClause = new StringBuilder();
        if(currentUser!=null && currentUser.getDeny()!=null)
            for(String deny:currentUser.getDeny()){
                securityClause.append(deny);
                if(counter++<currentUser.getDeny().length)// check to find out is the length correct or not
                    securityClause.append(" OR ");
            }

        query = queryParser.parse(securityClause.toString());
        queryBilder.add(query, BooleanClause.Occur.MUST_NOT);
    }
}
