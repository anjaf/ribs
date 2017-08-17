package uk.ac.ebi.biostudies.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.lucene.facet.FacetResult;
import org.apache.lucene.search.Query;
import uk.ac.ebi.biostudies.api.BioStudiesField;

import java.util.List;
import java.util.Map;

/**
 * Created by ehsan on 09/03/2017.
 */
public interface FacetService {

    List<FacetResult> getFacetsForQuery(Query query);
    JsonNode getDefaultFacetTemplate(String prjName);
    Query addFacetDrillDownFilters(Query primaryQuery, Map<BioStudiesField, List<String>> userSelectedDimValues);
}
