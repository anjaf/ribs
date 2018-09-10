package uk.ac.ebi.biostudies.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import uk.ac.ebi.biostudies.api.util.BioStudiesQueryParser;
import uk.ac.ebi.biostudies.api.util.Constants;
import uk.ac.ebi.biostudies.api.util.analyzer.AnalyzerManager;
import uk.ac.ebi.biostudies.api.util.analyzer.AttributeFieldAnalyzer;
import uk.ac.ebi.biostudies.config.IndexConfig;
import uk.ac.ebi.biostudies.config.IndexManager;
import uk.ac.ebi.biostudies.config.TaxonomyManager;
import uk.ac.ebi.biostudies.efo.EFOExpansionTerms;
import uk.ac.ebi.biostudies.efo.EFOQueryExpander;
import uk.ac.ebi.biostudies.service.FacetService;
import uk.ac.ebi.biostudies.service.QueryService;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class QueryServiceImpl implements QueryService {

    private Logger logger = LogManager.getLogger(QueryServiceImpl.class.getName());


    @Autowired
    IndexConfig indexConfig;
    @Autowired
    IndexManager indexManager;
    @Autowired
    EFOQueryExpander efoQueryExpander;
    @Autowired
    AnalyzerManager analyzerManager;
    @Autowired
    SecurityQueryBuilder securityQueryBuilder;
    @Autowired
    FacetService facetService;
    @Autowired
    TaxonomyManager taxonomyManager;

    private static Query excludeCompound;
    private static Query excludeProject;
    private QueryParser parser;
    private static Query excludeFiles;


    @PostConstruct
    void init(){
        parser = new QueryParser(Constants.Fields.TYPE, new AttributeFieldAnalyzer());
        parser.setSplitOnWhitespace(true);
        try {
            excludeCompound = parser.parse("type:compound");
            excludeProject = parser.parse("type:project");
            excludeFiles = parser.parse("type:file");
        } catch (ParseException e) {
            logger.error(e);
        }
    }

    @Override
    public Pair<Query, EFOExpansionTerms> makeQuery(String queryString, String projectName, JsonNode selectedFields) {

        String[] fields = indexConfig.getIndexFields();
        if (StringUtils.isEmpty(queryString)) {
            queryString = "*:*";
        }
        Analyzer analyzer = analyzerManager.getPerFieldAnalyzerWrapper();
        QueryParser parser = new BioStudiesQueryParser(fields, analyzer, indexManager);
        parser.setSplitOnWhitespace(true);
        Pair<Query, EFOExpansionTerms> finalQuery = null;
        try {
            logger.debug("User queryString: {}", queryString);
            Query query = parser.parse(queryString);
            Pair<Query, EFOExpansionTerms> queryEFOExpansionTermsPair = expandQuery(query);
            Query expandedQuery = null;
            if (selectedFields != null)
                expandedQuery = applySelectedFields((ObjectNode) selectedFields, queryEFOExpansionTermsPair.getKey(), parser);
            expandedQuery = (expandedQuery == null ? queryEFOExpansionTermsPair.getKey() : expandedQuery);
            expandedQuery = excludeCompoundStudies(expandedQuery);

            if (!queryString.toLowerCase().contains("type:")){
                if (selectedFields!=null && !selectedFields.has("type")) {
                    expandedQuery = excludeProjects(expandedQuery);
                }
            }
            if(!StringUtils.isEmpty(projectName) && !projectName.equalsIgnoreCase(Constants.PUBLIC)) {
                expandedQuery = applyProjectFilter(expandedQuery, projectName);
            }
            Query queryAfterSecurity = securityQueryBuilder.applySecurity(expandedQuery);
            logger.debug("Lucene query: {}",queryAfterSecurity.toString());
            finalQuery =  new MutablePair<>(queryAfterSecurity, queryEFOExpansionTermsPair.getValue());
        } catch (Throwable ex){
            logger.error(ex);
        }
        return finalQuery;
    }

    public Query applyProjectFilter(Query query, String prjName){
        /*
        QueryParser searchPrjParser = new QueryParser(Constants.PROJECT, new AttributeFieldAnalyzer());
        searchPrjParser.setSplitOnWhitespace(true);
        BooleanQuery.Builder bqBuilder = new BooleanQuery.Builder();
        bqBuilder.add(query, BooleanClause.Occur.MUST);
        try {
            String prjSearch = "("+Constants.PROJECT+":"+prjName+")";
            Query searchInPrj = searchPrjParser.parse(prjSearch);
            bqBuilder.add(searchInPrj, BooleanClause.Occur.MUST);
        } catch (ParseException e) {
            logger.debug(e);
        }
        return bqBuilder.build();
        */
        Map<JsonNode, List<String>> hm = new HashMap<JsonNode, List<String>>();
        hm.put(taxonomyManager.PROJECT_FACET, Lists.newArrayList(prjName));
        return facetService.addFacetDrillDownFilters(query, hm);
    }

    public Pair<Query, EFOExpansionTerms> expandQuery(Query query){
        Map<String, String> queryInfo = analyzerManager.getExpandableFields();
        try {
            return efoQueryExpander.expand(queryInfo, query);
        }catch (Exception ex){
            logger.error("Problem in expanding query {}", query, ex);
        }
        return new MutablePair<>(query, null);
    }

    private Query excludeCompoundStudies(Query originalQuery){
        BooleanQuery.Builder excludeBuilder = new BooleanQuery.Builder();
        excludeBuilder.add(originalQuery, BooleanClause.Occur.MUST);
        excludeBuilder.add(excludeCompound, BooleanClause.Occur.MUST_NOT);
        excludeBuilder.add(excludeFiles, BooleanClause.Occur.MUST_NOT);
        return  excludeBuilder.build();
    }

    private Query excludeProjects(Query originalQuery){
        BooleanQuery.Builder excludeBuilder = new BooleanQuery.Builder();
        excludeBuilder.add(originalQuery, BooleanClause.Occur.MUST);
        excludeBuilder.add(excludeProject, BooleanClause.Occur.MUST_NOT);
        return  excludeBuilder.build();
    }

    private Query applySelectedFields(ObjectNode selectedFields, Query query, QueryParser queryParser){
        Iterator<String> fieldNamesIterator = selectedFields.fieldNames();
        String fieldName = "";
        String fieldValue = "";
        BooleanQuery.Builder fieldQueryBuilder = new BooleanQuery.Builder();
        fieldQueryBuilder.add(query, BooleanClause.Occur.MUST);
        while(fieldNamesIterator.hasNext()) {
            try {
                fieldName = fieldNamesIterator.next();
                if (fieldName == null|| fieldName.isEmpty() || fieldName.equalsIgnoreCase("query"))
                    continue;
                fieldValue = selectedFields.get(fieldName).textValue();
                if(fieldValue==null || fieldValue.isEmpty())
                    continue;
                Query fieldQuery = queryParser.parse(fieldName+":"+fieldValue);
                fieldQueryBuilder.add(fieldQuery, BooleanClause.Occur.MUST);
            }
            catch (Exception ex){
                logger.error("field {} value {} has problem for lucene queryparser", fieldName, fieldValue, ex);
            }
        }
        return fieldQueryBuilder.build();
    }

}
