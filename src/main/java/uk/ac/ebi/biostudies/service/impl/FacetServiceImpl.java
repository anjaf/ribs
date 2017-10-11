package uk.ac.ebi.biostudies.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.facet.*;
import org.apache.lucene.facet.taxonomy.FastTaxonomyFacetCounts;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.ebi.biostudies.api.util.Constants;
import uk.ac.ebi.biostudies.config.IndexManager;
import uk.ac.ebi.biostudies.config.TaxonomyManager;
import uk.ac.ebi.biostudies.service.FacetService;
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
    IndexManager indexManager;
    @Autowired
    TaxonomyManager taxonomyManager;
    @Autowired
    SecurityQueryBuilder securityQueryBuilder;
    @Autowired
    TextService textService;
    private JsonNode hecatosFacets;


    @Override
    public List<FacetResult> getFacetsForQuery(Query query) {
        FacetsCollector facetsCollector = new FacetsCollector();
        List<FacetResult> allResults = new ArrayList();
        try {
            query = securityQueryBuilder.applySecurity(query);
            FacetsCollector.search(indexManager.getIndexSearcher(), query, 20, facetsCollector);
            Facets facets = new FastTaxonomyFacetCounts(taxonomyManager.getTaxonomyReader(), taxonomyManager.getFacetsConfig(), facetsCollector);
            for (JsonNode field:indexManager.getAllValidFields().values()) {
                if(field.get("fieldType").asText().equalsIgnoreCase("facet")) {
                    allResults.add(facets.getTopChildren(20, field.get("name").asText()));
                }
            }
        } catch (IOException e) {
            logger.debug("problem in creating facetresults for this query {}", query, e);
        } catch (Throwable e) {
            logger.debug("problem in applying security in creating facetresults for this query {}", query, e);
        }
        return allResults;
        //                searcher.search(new MatchAllDocsQuery(), facetsCollector); new MatchAllDocsQuery()
    }

    @Override
    public JsonNode getDefaultFacetTemplate(String prjName){
        QueryParser qp = new QueryParser(Constants.PROJECT, new SimpleAnalyzer());
        qp.setSplitOnWhitespace(true);
        Query query = null;
        try {
            query = qp.parse(Constants.PROJECT+":"+prjName);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<FacetResult> facetResults = getFacetsForQuery(query);
        ObjectMapper mapper = new ObjectMapper();
        List<ObjectNode> list = new ArrayList<>();
        Set<String> validFacets = indexManager.getAllValidFields().keySet();
        for(FacetResult fcResult:facetResults){
            if(fcResult==null)
                continue;
            if(!validFacets.contains(fcResult.dim))
                continue;
            ObjectNode facet = mapper.createObjectNode();
            JsonNode facetNode = indexManager.getAllValidFields().get(fcResult.dim);
            facet.put("title", facetNode.get("title").asText());
            facet.put("name", facetNode.get("name").asText());
            List<ObjectNode> children = new ArrayList<>();
            for(LabelAndValue labelVal :fcResult.labelValues){
                ObjectNode child = mapper.createObjectNode();
                child.put("name",  textService.getNormalisedString(labelVal.label));
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
