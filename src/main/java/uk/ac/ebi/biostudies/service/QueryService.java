package uk.ac.ebi.biostudies.service;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.lucene.search.Query;
import uk.ac.ebi.biostudies.efo.EFOExpansionTerms;


public interface QueryService {
    Pair<Query, EFOExpansionTerms> makeQuery(String queryString, String projectName);
    Query applyProjectFilter(Query query, String prjName);
}
