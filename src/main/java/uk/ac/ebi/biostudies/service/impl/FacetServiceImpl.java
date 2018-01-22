package uk.ac.ebi.biostudies.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.facet.*;
import org.apache.lucene.facet.taxonomy.FastTaxonomyFacetCounts;
import org.apache.lucene.search.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.ebi.biostudies.api.util.Constants;
import uk.ac.ebi.biostudies.api.util.analyzer.AnalyzerManager;
import uk.ac.ebi.biostudies.auth.Session;
import uk.ac.ebi.biostudies.config.IndexConfig;
import uk.ac.ebi.biostudies.config.IndexManager;
import uk.ac.ebi.biostudies.config.TaxonomyManager;
import uk.ac.ebi.biostudies.service.FacetService;
import uk.ac.ebi.biostudies.service.QueryService;
import uk.ac.ebi.biostudies.service.TextService;

import java.io.IOException;
import java.util.*;

/**
 * Created by ehsan on 09/03/2017.
 */

@Service
public class FacetServiceImpl implements FacetService {

    private Logger logger = LogManager.getLogger(FacetServiceImpl.class.getName());

    @Autowired
    IndexConfig indexConfig;
    @Autowired
    IndexManager indexManager;
    @Autowired
    TaxonomyManager taxonomyManager;
    @Autowired
    SecurityQueryBuilder securityQueryBuilder;
    @Autowired
    TextService textService;
    @Autowired
    AnalyzerManager analyzerManager;
    @Autowired
    QueryService queryService;

    final int FACET_LIMIT = 21;


    @Override
    public List<FacetResult> getFacetsForQuery(Query query) {
        FacetsCollector facetsCollector = new FacetsCollector();
        List<FacetResult> allResults = new ArrayList();
        try {
            query = securityQueryBuilder.applySecurity(query);
            FacetsCollector.search(indexManager.getIndexSearcher(), query, FACET_LIMIT, facetsCollector);
            Facets facets = new FastTaxonomyFacetCounts(taxonomyManager.getTaxonomyReader(), taxonomyManager.getFacetsConfig(), facetsCollector);
            for (JsonNode field:indexManager.getAllValidFields().values()) {
                if(field.get("fieldType").asText().equalsIgnoreCase("facet")){
                    if(field.has("isPrivate") && field.get("isPrivate").asBoolean()==true && Session.getCurrentUser()==null) {
                        continue;
                    }
                    allResults.add(facets.getTopChildren(FACET_LIMIT, field.get("name").asText()));
                }
            }
        } catch (IOException e) {
            logger.debug("problem in creating facetresults for this query {}", query, e);
        } catch (Throwable e) {
            logger.debug("problem in applying security in creating facetresults for this query {}", query, e);
        }
        return allResults;
    }

    @Override
    public JsonNode getDefaultFacetTemplate(String prjName, String queryString){
        Query query = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            query = queryService.makeQuery(queryString, prjName).getKey();
        } catch (NullPointerException e) {
            logger.debug("problem in parsing query {}", queryString, e);
        }
        List<FacetResult> facetResults = getFacetsForQuery(query);
        List<ObjectNode> list = new ArrayList<>();
        Set<String> validFacets = indexManager.getProjectRelatedFields(prjName.toLowerCase());
        for (FacetResult fcResult : facetResults) {
            if (fcResult == null || !validFacets.contains(fcResult.dim)) {
                continue;
            }
            ObjectNode facet = mapper.createObjectNode();
            JsonNode facetNode = indexManager.getAllValidFields().get(fcResult.dim);
            if (!prjName.equalsIgnoreCase(Constants.PUBLIC) && facetNode == taxonomyManager.PROJECT_FACET) {
                continue;
            }
            boolean invisNA = false;
            String naDefaultStr = Constants.NA;
            if(facetNode.has(Constants.IS_VISIBLE) && facetNode.get(Constants.IS_VISIBLE).asBoolean()==false)
                invisNA = true;
            if(facetNode.has(Constants.DEFAULT_VALUE))
                naDefaultStr = facetNode.get(Constants.DEFAULT_VALUE).asText();
            facet.put("title", facetNode.get("title").asText());
            facet.put("name", facetNode.get("name").asText());
            List<ObjectNode> children = new ArrayList<>();
            for (LabelAndValue labelVal : fcResult.labelValues) {
                if(invisNA && labelVal.label.equalsIgnoreCase(naDefaultStr))
                    continue;
                if(children.size()==FACET_LIMIT)
                    break;
                ObjectNode child = mapper.createObjectNode();
                child.put("name", textService.getNormalisedString(labelVal.label));
                child.put("value", labelVal.label);
                child.put("hits", labelVal.value.intValue());
                children.add(child);
            }
            Collections.sort(children, Comparator.comparing(o -> o.get("name").textValue()));
            ArrayNode childrenArray = mapper.createArrayNode();
            childrenArray.addAll(children);
            facet.set("children", childrenArray);
            list.add(facet);
        }
        Collections.sort(list, Comparator.comparing(o -> o.get("title").textValue()));
        return mapper.createArrayNode().addAll(list);
    }


    @Override
    public Query addFacetDrillDownFilters(Query primaryQuery, Map<JsonNode, List<String>> userSelectedDimValues){
        DrillDownQuery drillDownQuery = new DrillDownQuery(taxonomyManager.getFacetsConfig(), primaryQuery);
        for(JsonNode facet: userSelectedDimValues.keySet()) {
            if (facet==null || !facet.get("fieldType").asText().equalsIgnoreCase("facet"))
                continue;
            List<String> listSelectedValues = userSelectedDimValues.get(facet);
            if(listSelectedValues!=null)
                for(String value:listSelectedValues)
                    drillDownQuery.add(facet.get("name").asText(), value.toLowerCase());
        }
        return drillDownQuery;
    }
}
