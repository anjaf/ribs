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
import uk.ac.ebi.biostudies.api.BioStudiesField;
import uk.ac.ebi.biostudies.api.BioStudiesFieldType;
import uk.ac.ebi.biostudies.config.IndexManager;
import uk.ac.ebi.biostudies.config.TaxonomyManager;
import uk.ac.ebi.biostudies.service.FacetService;

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


    private JsonNode hecatosFacets;


    @Override
    public List<FacetResult> getFacetsForQuery(Query query) {
        FacetsCollector facetsCollector = new FacetsCollector();
        List<FacetResult> allResults = new ArrayList();
        try {
            query = securityQueryBuilder.applySecurity(query);
            FacetsCollector.search(indexManager.getIndexSearcher(), query, 10, facetsCollector);
            Facets facets = new FastTaxonomyFacetCounts(taxonomyManager.getTaxonomyReader(), taxonomyManager.getFacetsConfig(), facetsCollector);
            for (BioStudiesField field:BioStudiesField.values()) {
                if(field.getType()== BioStudiesFieldType.FACET) {
                    allResults.add(facets.getTopChildren(20, field.toString()));
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
        QueryParser qp = new QueryParser(BioStudiesField.PROJECT.toString(), new SimpleAnalyzer());
        Query query = null;
        try {
            query = qp.parse(BioStudiesField.PROJECT+":"+prjName);
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
            //BioStudiesField field = BioStudiesField.getFacet(fcResult.dim);
            JsonNode facetNode = indexManager.getAllValidFields().get(fcResult.dim);
            facet.put("title", facetNode.get("title").asText());
            facet.put("name", facetNode.get("name").asText());
            List<ObjectNode> children = new ArrayList<>();
            for(LabelAndValue labelVal :fcResult.labelValues){
                ObjectNode child = mapper.createObjectNode();
                child.put("name", labelVal.label);
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
    public Query addFacetDrillDownFilters(Query primaryQuery, Map<BioStudiesField, List<String>> userSelectedDimValues){
        DrillDownQuery drillDownQuery = new DrillDownQuery(taxonomyManager.getFacetsConfig(), primaryQuery);
        for(BioStudiesField facet: userSelectedDimValues.keySet()) {
            if (facet==null || facet.getType() != BioStudiesFieldType.FACET)
                continue;
            List<String> listSelectedValues = userSelectedDimValues.get(facet);
            if(listSelectedValues!=null)
                for(String value:listSelectedValues)
                    drillDownQuery.add(facet.toString(), value);
        }
        return drillDownQuery;
    }
}
