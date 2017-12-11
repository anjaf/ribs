package uk.ac.ebi.biostudies.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.spell.LuceneDictionary;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.store.RAMDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import uk.ac.ebi.biostudies.api.util.Constants;
import uk.ac.ebi.biostudies.api.util.StudyUtils;
import uk.ac.ebi.biostudies.api.util.analyzer.AnalyzerManager;
import uk.ac.ebi.biostudies.api.util.analyzer.AttributeFieldAnalyzer;
import uk.ac.ebi.biostudies.efo.Autocompletion;
import uk.ac.ebi.biostudies.efo.EFOExpandedHighlighter;
import uk.ac.ebi.biostudies.efo.EFOExpansionTerms;
import uk.ac.ebi.biostudies.efo.EFOQueryExpander;
import uk.ac.ebi.biostudies.config.IndexConfig;
import uk.ac.ebi.biostudies.service.FacetService;
import uk.ac.ebi.biostudies.service.QueryService;
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
    @Autowired
    QueryService queryService;

    private static Query excludeCompound;
    private static QueryParser parser;
    @PostConstruct
    void init(){
        parser = new QueryParser(Constants.TYPE, new AttributeFieldAnalyzer());
        parser.setSplitOnWhitespace(true);
        try {
            excludeCompound = parser.parse("type:compound");
        } catch (ParseException e) {
            logger.error(e);
        }
    }

    @Override
    public String getKeywords(String query, String field, Integer limit) {
        return autocompletion.getKeywords(query, field, limit);
    }

    @Override
    public String getEfoTree(String query) {
        return autocompletion.getEfoChildren(query);
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

    private Query applyFacets(Query query, JsonNode facets){
        Map<JsonNode, List<String>> selectedFacets = new HashMap<>();
        if(facets!=null){
            Iterator<String> fieldNamesIterator = facets.fieldNames();
            String dim="";
            while(fieldNamesIterator.hasNext()){
                try {
                    dim = fieldNamesIterator.next();
                    if(dim==null)
                        continue;
                    JsonNode field = indexManager.getAllValidFields().get(dim);
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
                    logger.debug("Invalid facet: {}", dim, ex);
                }
            }
        }
        return  facetService.addFacetDrillDownFilters(query, selectedFacets);
    }

    private ObjectNode applySearchOnQuery(Query query, int page, int pageSize, String sortBy, String sortOrder, boolean doHighlight, String queryString){
        IndexReader reader = indexManager.getIndexReader();
        IndexSearcher searcher = indexManager.getIndexSearcher();
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode response = mapper.createObjectNode();
        SortField.Type sortFieldType = extractFieldType(sortBy);
        boolean shouldReverse = extractSortOrder(sortOrder, sortBy);
        SortField sortField;
        if(sortBy.equalsIgnoreCase(Constants.RELEVANCE))
            sortField = new SortField (null, SortField.Type.SCORE, shouldReverse);
        else {
            sortField = sortFieldType == SortField.Type.LONG
                    ? new SortedNumericSortField(sortBy, sortFieldType, shouldReverse)
                    : new SortField(sortBy, sortFieldType, shouldReverse);
        }
        Sort sort = new Sort( sortField );
        if(sortBy.equalsIgnoreCase(Constants.RELEASE_DATE))
            sort = new Sort(sortField, new SortField(Constants.CREATION_TIME, SortField.Type.STRING, shouldReverse));

        try {
            TopDocs hits = searcher.search(query, Integer.MAX_VALUE , sort);
            int hitsPerPage = pageSize;
            long to = page * hitsPerPage > hits.totalHits ? hits.totalHits : page * hitsPerPage;
            response.put("page", page);
            response.put("pageSize", hitsPerPage);
            response.put("totalHits", hits.totalHits);
            response.put( "sortBy", sortBy);
            response.put( "sortOrder", sortOrder);
            if (hits.totalHits > 0) {
                ArrayNode docs = mapper.createArrayNode();
                for (int i = (page - 1) * hitsPerPage; i < to; i++) {
                    ObjectNode docNode = mapper.createObjectNode();
                    Document doc = reader.document(hits.scoreDocs[i].doc);
                    for (String field : indexManager.getAllValidFields().keySet()) {
                        JsonNode fieldData = indexManager.getAllValidFields().get(field);
                        if (!fieldData.has("isRetrieved") || fieldData.get("isRetrieved").asText().equalsIgnoreCase("false")) continue;
                        switch (fieldData.get("fieldType").asText()) {
                            case "long":
                                docNode.put(field, Long.parseLong(doc.get(field)));
                                break;
                            default:
                                docNode.put(field, doc.get(field));
                                break;
                        }
                    }
                    docNode.put("isPublic",
                            (" " + doc.get(Constants.ACCESS) + " ").toLowerCase().contains(" public ")
                    );

                    if (doHighlight) {
                        docNode.put(Constants.CONTENT,
                                efoExpandedHighlighter.highlightQuery(query, Constants.CONTENT,
                                        doc.get(Constants.CONTENT),
                                        true
                                )
                        );
                    }
                    docs.add(docNode);
                }
                response.set("hits", docs);
                logger.debug(hits.totalHits + " hits");
            }
            else{
                String[]spells = indexManager.getSpellChecker().suggestSimilar(queryString, 5);
                response.set("suggestion", mapper.valueToTree(Arrays.asList(spells)));
            }
        }
        catch(Throwable error){
            logger.error("problem in searching this query {}", query, error);
        }
        return  response;
    }

    private SortField.Type extractFieldType(String sortBy){
        try{
            if(sortBy == null || sortBy.isEmpty() || sortBy.equalsIgnoreCase("relevance"))
                return SortField.Type.LONG;
            else{
                JsonNode field = indexManager.getAllValidFields().get(sortBy.toLowerCase());
                return field.get("fieldType").asText().toLowerCase().contains("str") ? SortField.Type.STRING : SortField.Type.LONG;
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
        return isAccessible(accession, null);
    }

    @Override
    public boolean isAccessible(String accession, String seckey) {
        QueryParser parser = new QueryParser(Constants.ACCESSION, new AttributeFieldAnalyzer());
        parser.setSplitOnWhitespace(true);
        Query query = null;
        try {
            query = parser.parse(Constants.ACCESSION+":"+accession);
            Query result = securityQueryBuilder.applySecurity(query, seckey);
            return indexManager.getIndexSearcher().count(result)>0;
        } catch (Throwable ex){
            logger.error("Problem in checking security", ex);
            return false;
        }
    }

    @Override
    public String search(String queryString, JsonNode selectedFacets, String prjName, int page, int pageSize, String sortBy, String sortOrder) {
        ObjectMapper mapper = new ObjectMapper();
        boolean doHighlight = true;
        if (StringUtils.isEmpty(queryString)) {
            doHighlight = false;
        }
        ObjectNode response = mapper.createObjectNode();
        Pair<Query, EFOExpansionTerms> resultPair = queryService.makeQuery(queryString, prjName);
        try {
            Query queryAfterSecurity = resultPair.getKey();
            if(selectedFacets!=null && prjName!=null && !prjName.isEmpty()){
                queryAfterSecurity = applyFacets(queryAfterSecurity, selectedFacets);
                logger.debug("Lucene after facet query: {}",queryAfterSecurity.toString());
            }
            response = applySearchOnQuery(queryAfterSecurity, page, pageSize, sortBy, sortOrder, doHighlight, queryString);

            // add expansion
            EFOExpansionTerms expansionTerms =  resultPair.getValue();
            if (expansionTerms!=null) {
                ArrayNode expandedEfoTerms = mapper.createArrayNode();
                expansionTerms.efo.forEach(s -> expandedEfoTerms.add(s));
                response.set("expandedEfoTerms", expandedEfoTerms);

                ArrayNode expandedSynonymTerms = mapper.createArrayNode();
                expansionTerms.synonyms.forEach(s -> expandedSynonymTerms.add(s));
                response.set("expandedSynonyms", expandedSynonymTerms);

            }

            response.put("query",  doHighlight ? queryString : null);
            response.set("facets", selectedFacets==null || selectedFacets.size()==0 ? null : selectedFacets);
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

    @Override
    public String getFieldStats() throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode response = mapper.createObjectNode();
        IndexSearcher searcher = indexManager.getIndexSearcher();

        DocValuesStats.SortedLongDocValuesStats  fileStats = new DocValuesStats.SortedLongDocValuesStats ("files");
        searcher.search(new MatchAllDocsQuery(), new DocValuesStatsCollector(fileStats));
        response.put("files", fileStats.sum());

        DocValuesStats.SortedLongDocValuesStats  linkStats = new DocValuesStats.SortedLongDocValuesStats ("links");
        searcher.search(new MatchAllDocsQuery(), new DocValuesStatsCollector(linkStats));
        response.put("links", linkStats.sum());

        return response.toString();
    }
}
