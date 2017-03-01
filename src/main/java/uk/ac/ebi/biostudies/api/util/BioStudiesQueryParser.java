package uk.ac.ebi.biostudies.api.util;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.Query;

/**
 * Created by awais on 16/02/2017.
 */
public class BioStudiesQueryParser extends MultiFieldQueryParser {

    public BioStudiesQueryParser(String[] fields, Analyzer analyzer) {
        super(fields, analyzer);
    }

    @Override
    public Query parse(String query) throws ParseException {
        if ( (" "+query.toLowerCase()).contains(" access:")) {
            throw new ParseException("Field 'access' not allowed in queries.");
        }
        return super.parse(query);
    }
}