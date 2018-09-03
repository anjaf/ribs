package uk.ac.ebi.biostudies.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.lucene.search.Query;
import uk.ac.ebi.biostudies.efo.EFOExpansionTerms;


public interface QueryService {
    Pair<Query, EFOExpansionTerms> makeQuery(String queryString, String projectName, JsonNode selectedFields);
    Query applyProjectFilter(Query query, String prjName);
}
