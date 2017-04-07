package uk.ac.ebi.biostudies.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.simple.SimpleQueryParser;
import org.apache.lucene.search.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import uk.ac.ebi.biostudies.api.BioStudiesField;
import uk.ac.ebi.biostudies.api.util.BioStudiesQueryParser;
import uk.ac.ebi.biostudies.api.util.Constants;
import uk.ac.ebi.biostudies.api.util.StudyUtils;
import uk.ac.ebi.biostudies.api.util.analyzer.AnalyzerManager;
import uk.ac.ebi.biostudies.efo.Autocompletion;
import uk.ac.ebi.biostudies.efo.EFOExpandedHighlighter;
import uk.ac.ebi.biostudies.efo.EFOQueryExpander;
import uk.ac.ebi.biostudies.config.IndexConfig;
import uk.ac.ebi.biostudies.service.FacetService;
import uk.ac.ebi.biostudies.service.SearchService;
import uk.ac.ebi.biostudies.config.IndexManager;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * Created by ehsan on 27/02/2017.
 */

@Service
public class SearchServiceImpl implements SearchService {

    private Logger logger = LogManager.getLogger(SearchServiceImpl.class.getName());

    @Autowired
    IndexConfig indexConfig;
    @Autowired
    IndexManager indexManager;
    @Autowired
    EFOQueryExpander efoQueryExpander;
    @Autowired
    EFOExpandedHighlighter efoExpandedHighlighter;
    @Autowired
    Autocompletion autocompletion;
    @Autowired
    FacetService facetService;
    @Autowired
    AnalyzerManager analyzerManager;
    @Autowired
    SecurityQueryBuilder securityQueryBuilder;

    private static Query excludeCompound;
    private static QueryParser parser;
    @PostConstruct
    void init(){
        parser = new QueryParser(BioStudiesField.TYPE.toString(), BioStudiesField.TYPE.getAnalyzer());
        try {
            excludeCompound = parser.parse("type:compound");
        } catch (ParseException e) {
            logger.error(e);
        }
    }

    public String highlightText(Query originalQuery, Query synonymQuery, Query efoQuery, String fieldName, String text, boolean fragmentOnly){
        String result = efoExpandedHighlighter.highlightQuery(originalQuery, synonymQuery, efoQuery, fieldName, text, fragmentOnly);
        return result;
    }

    @Override
    public String getKeywords(String query, String field, Integer limit) {
       return autocompletion.getKeywords(query, field, limit);
    }

    @Override
    public String getEfoWords(String query, Integer limit) {
        return autocompletion.getEfoWords(query, limit);
    }

    public Query expandQuery(Query originalQuery, BooleanQuery.Builder synonymBooleanBuilder, BooleanQuery.Builder efoBooleanBuilder){
        Map<String, String> queryInfo = analyzerManager.getExpandableFields();
        Query modifiedQuery = null;
        try {
            modifiedQuery = efoQueryExpander.expand(queryInfo, originalQuery, synonymBooleanBuilder, efoBooleanBuilder);
        }catch (Exception ex){
            logger.error("problem in expanding query {}", originalQuery, ex);
            return originalQuery;
        }
        return modifiedQuery;
    }

    private Query excludeCompoundStudies(Query originalQuery){
        BooleanQuery.Builder excludeBuilder = new BooleanQuery.Builder();
        excludeBuilder.add(originalQuery, BooleanClause.Occur.MUST);
        excludeBuilder.add(excludeCompound, BooleanClause.Occur.MUST_NOT);
        return  excludeBuilder.build();
    }

    @Override
    public Query applyFacets(Query query, JsonNode facets){
        Map<BioStudiesField, List<String>> selectedFacets = new HashMap<>();
        if(facets!=null){
            Iterator<String> fieldNamesIterator = facets.fieldNames();
            String dim="";
            while(fieldNamesIterator.hasNext()){
                try {
                    dim = fieldNamesIterator.next();
                    if(dim==null)
                        continue;
                    BioStudiesField field = BioStudiesField.valueOf(dim.toUpperCase());
                    JsonNode arrNode = facets.get(dim);
                    List<String> facetNames = new ArrayList<>();
                    if(arrNode==null)
                        continue;
                    selectedFacets.put(field, facetNames);
                    if(arrNode.isArray())
                        for (final JsonNode objNode : arrNode)
                        {
                            facetNames.add(objNode.textValue());
                        }
                }catch (Throwable ex){
                    logger.debug("ui sent invalid facet: {}", dim, ex);
                }
            }
        }
        return  facetService.addFacetDrillDownFilters(query, selectedFacets);
    }

    @Override
    public ObjectNode applySearchOnQuery(Query query, int page, int pageSize, String sortBy, String sortOrder) throws ParseException {
        Query emptyQuery = parser.parse("");
        return applySearchOnQuery(query, emptyQuery, emptyQuery, page, pageSize, sortBy, sortOrder);
    }

    public ObjectNode applySearchOnQuery(Query query, Query synonymQuery, Query efoQuery, int page, int pageSize, String sortBy, String sortOrder){
        IndexReader reader = indexManager.getIndexReader();
        IndexSearcher searcher = indexManager.getIndexSearcher();
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode response = mapper.createObjectNode();
        SortField.Type sortFieldType = extractFieldType(sortBy);
        boolean shouldReverse = extractSortOrder(sortOrder, sortBy);

        SortField sortField = sortFieldType==SortField.Type.LONG
                ? new SortedNumericSortField (sortBy, sortFieldType, shouldReverse)
                : new SortField(sortBy, sortFieldType, shouldReverse);
        Sort sort = new Sort( sortField );

        try {
            TopDocs hits = searcher.search(query, Integer.MAX_VALUE , sort);
            int hitsPerPage = pageSize;
            int to = page * hitsPerPage > hits.totalHits ? hits.totalHits : page * hitsPerPage;
            response.put("page", page);
            response.put("pageSize", hitsPerPage);
            response.put("totalHits", hits.totalHits);
            if (hits.totalHits > 0) {
                ArrayNode docs = mapper.createArrayNode();
                for (int i = (page - 1) * hitsPerPage; i < to; i++) {
                    ObjectNode docNode = mapper.createObjectNode();
                    Document doc = reader.document(hits.scoreDocs[i].doc);
                    for (BioStudiesField field : BioStudiesField.values()) {
                        if (!field.isRetrieved()) continue;
                        switch (field.getType()) {
                            case LONG:
                                docNode.put(String.valueOf(field), Long.parseLong(doc.get(field.toString())));
                                break;
                            default:
                                docNode.put(String.valueOf(field), doc.get(field.toString()));
                                break;
                        }
                    }
                    docNode.put("isPublic",
                            (" " + doc.get(String.valueOf(BioStudiesField.ACCESS) + " ")).toLowerCase().contains(" public ")
                    );
                    docNode.put(String.valueOf(BioStudiesField.CONTENT),
                            efoExpandedHighlighter.highlightQuery( query, synonymQuery, efoQuery,
                                    BioStudiesField.CONTENT.toString(),
                                    doc.get(BioStudiesField.CONTENT.toString()),
                                    true
                            )
                    );
                    docs.add(docNode);
                }
                response.set("hits", docs);
                logger.debug(hits.totalHits + " hits");
            }
        }
        catch(Throwable error){
            logger.error("problem in searching this query {}", query, error);
        }
        return  response;
    }

    private SortField.Type extractFieldType(String sortBy){
       try{
           if(!sortBy.isEmpty()) {
               BioStudiesField field = BioStudiesField.valueOf(sortBy.toUpperCase());
               return field.getType().toString().toLowerCase().contains("string") ? SortField.Type.STRING : SortField.Type.LONG;
           }
       }
       catch (Exception e){
            logger.debug("bad sortby value {}", sortBy);
       }
       return SortField.Type.SCORE;
    }
    private boolean extractSortOrder(String sortOrder, String sortBy){
        if(sortOrder.isEmpty()) {
            if (Constants.ACCESSION.equalsIgnoreCase(sortBy) || Constants.TITLE.equalsIgnoreCase(sortBy) || Constants.AUTHORS.equalsIgnoreCase(sortBy))
                sortOrder = Constants.ASCENDING;
            else
                sortOrder = Constants.DESCENDING;
        }
        boolean shouldReverse =  (Constants.DESCENDING.equalsIgnoreCase(sortOrder) ? true : false);
        if (sortBy ==null || Constants.RELEVANCE.equalsIgnoreCase(sortBy) ) {
            shouldReverse = !shouldReverse;
        }
        return shouldReverse;
    }

    @Override
    public boolean isAccessible(String accession) {
        QueryParser parser = new QueryParser(BioStudiesField.ACCESSION.toString(), BioStudiesField.ACCESSION.getAnalyzer());
        Query query = null;
        try {
            query = parser.parse(BioStudiesField.ACCESSION.toString()+":"+accession);
            Query result = securityQueryBuilder.applySecurity(query);
            return indexManager.getIndexSearcher().count(result)>0;
        } catch (Throwable ex){
            logger.error("Problem in checking security", ex);
            return false;
        }
    }

    @Override
    public String search(String queryString, int page, int pagesize, String sortBy, String sortOrder) {
        String[] fields = indexConfig.getIndexFields();
        ObjectMapper mapper = new ObjectMapper();

        if (StringUtils.isEmpty(queryString))
            queryString = "*:*";
        Analyzer analyzer = analyzerManager.getPerFieldAnalyzerWrapper();
        QueryParser parser = new BioStudiesQueryParser(fields, analyzer);
        ObjectNode response = mapper.createObjectNode();
        try {
            logger.debug("User queryString: {}",queryString);
            Query query = parser.parse(queryString.toLowerCase());
            BooleanQuery.Builder synonymQueryBuilder = new BooleanQuery.Builder();
            BooleanQuery.Builder efoQueryBuilder = new BooleanQuery.Builder();
            Query expandedQuery = expandQuery(query, synonymQueryBuilder, efoQueryBuilder);
            expandedQuery = excludeCompoundStudies(expandedQuery);
            Query queryAfterSecurity = securityQueryBuilder.applySecurity(expandedQuery);
            logger.debug("Lucene query: {}",queryAfterSecurity.toString());
            response = applySearchOnQuery(queryAfterSecurity, synonymQueryBuilder.build(), efoQueryBuilder.build(),
                    page, pagesize, sortBy, sortOrder);
            response.set("expandedEfoTerms", mapper.createArrayNode() );
            response.set("expandedSynonyms", mapper.createArrayNode() );
            response.put("query",  queryString.equals("*:*") ? null : queryString);
        }
        catch(Throwable error){
            logger.error("problem in searching this query {}", queryString, error);
            response.put("query",  queryString.equals("*:*") ? null : queryString);
        }

        return  response.toString();
    }

    @Override
    public String getDetailFile(String accessionNumber) throws IOException {
        String path = StudyUtils.getPartitionedPath(accessionNumber);
        File file = new File(indexConfig.getRepositoryPath() + path + "/"+accessionNumber+".json");
        if (!file.exists()) {
            throw new FileNotFoundException("Study "+accessionNumber+" not found!");        }
        int length = (int)file.length();
        FileInputStream reader  = new FileInputStream(file);
        byte[] buff = new byte[length];
        reader.read(buff);
        String result = new String(buff, "UTF-8");
        return result;
    }
}
