package uk.ac.ebi.biostudies.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queries.mlt.MoreLikeThis;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
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
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static java.nio.charset.StandardCharsets.UTF_8;
import static uk.ac.ebi.biostudies.api.util.Constants.*;
import static uk.ac.ebi.biostudies.controller.Stats.LATEST_ENDPOINT;
import static uk.ac.ebi.biostudies.controller.Stats.STATS_ENDPOINT;

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


    private static Cache<String, String> statsCache;
    private static Query excludeCompound;
    private static QueryParser parser;
    @PostConstruct
    void init(){
        parser = new QueryParser(Fields.TYPE, new AttributeFieldAnalyzer());
        parser.setSplitOnWhitespace(true);
        // TODO: move stats cache timeout to config file
        statsCache = CacheBuilder.newBuilder()
                .expireAfterAccess(720, TimeUnit.MINUTES)
                .build();
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

    private ObjectNode applySearchOnQuery(Query query, int page, int pageSize, String sortBy, String sortOrder, boolean doHighlight, String queryString){
        IndexReader reader = indexManager.getIndexReader();
        IndexSearcher searcher = indexManager.getIndexSearcher();
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode response = mapper.createObjectNode();
        SortField.Type sortFieldType = extractFieldType(sortBy);
        boolean shouldReverse = extractSortOrder(sortOrder, sortBy);
        SortField sortField;
        if(sortBy.equalsIgnoreCase(RELEVANCE))
            sortField = new SortField (null, SortField.Type.SCORE, shouldReverse);
        else {
            sortField = sortFieldType == SortField.Type.LONG
                    ? new SortedNumericSortField(sortBy, sortFieldType, shouldReverse)
                    : new SortField(sortBy, sortFieldType, shouldReverse);
        }
        Sort sort = new Sort( sortField );
        if(sortBy.equalsIgnoreCase(RELEASE_DATE))
            sort = new Sort(sortField, new SortedNumericSortField(Fields.MODIFICATION_TIME, SortField.Type.LONG, shouldReverse));

        try {
            TopDocs hits = searcher.search(query, Integer.MAX_VALUE , sort);
            int hitsPerPage = pageSize;
            long totalHits = hits.totalHits!=null ? hits.totalHits.value :0;
            long to = page * hitsPerPage > totalHits ? totalHits : page * hitsPerPage;
            response.put("page", page);
            response.put("pageSize", hitsPerPage);
            response.put("totalHits", totalHits);
            response.put( "sortBy", sortBy.equalsIgnoreCase(Fields.RELEASE_TIME) ? RELEASE_DATE : sortBy);
            response.put( "sortOrder", sortOrder);
            if (totalHits > 0) {
                ArrayNode docs = mapper.createArrayNode();
                for (int i = (page - 1) * hitsPerPage; i < to; i++) {
                    ObjectNode docNode = mapper.createObjectNode();
                    Document doc = reader.document(hits.scoreDocs[i].doc);
                    for (String field : indexManager.getIndexEntryMap().keySet()) {
                        JsonNode fieldData = indexManager.getIndexEntryMap().get(field);
                        if ( fieldData.has(IndexEntryAttributes.RETRIEVED)
                                && fieldData.get(IndexEntryAttributes.RETRIEVED).asBoolean(false)) {
                            if (fieldData.get(IndexEntryAttributes.FIELD_TYPE).asText().equalsIgnoreCase(IndexEntryAttributes.FieldTypeValues.LONG)
                                 && doc.get(field) != null) {
                                        docNode.put(field, Long.parseLong(doc.get(field)));
                            } else {
                                docNode.put(field, doc.get(field));
                            }
                        }
                    }
                    boolean isPublic = (" " + doc.get(Fields.ACCESS) + " ").toLowerCase().contains(" public ");
                    docNode.put("isPublic", isPublic);
                    if (!isPublic) {
                        docNode.put("modification_time", doc.get(Fields.MODIFICATION_TIME));
                    }

                    if (doHighlight) {
                        docNode.put(Fields.CONTENT,
                                efoExpandedHighlighter.highlightQuery(query, Fields.CONTENT,
                                        doc.get(Fields.CONTENT),
                                        true
                                )
                        );
                    }
                    docs.add(docNode);
                }
                response.set("hits", docs);
                logger.debug(hits.totalHits + " hits");
            }

            else if(queryString!=null && !queryString.isEmpty()){
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
                JsonNode field = indexManager.getIndexEntryMap().get(sortBy.toLowerCase());
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
            sortOrder  = (Fields.ACCESSION.equalsIgnoreCase(sortBy) || Fields.TITLE.equalsIgnoreCase(sortBy) || Fields.AUTHOR.equalsIgnoreCase(sortBy))
                    ? SortOrder.ASCENDING : SortOrder.DESCENDING;
        }
        boolean shouldReverse =  (SortOrder.DESCENDING.equalsIgnoreCase(sortOrder) ? true : false);
        if (sortBy ==null || RELEVANCE.equalsIgnoreCase(sortBy) ) {
            shouldReverse = !shouldReverse;
        }
        return shouldReverse;
    }

    @Override
    public String search(String queryString, JsonNode selectedFacetsAndFields, String prjName, int page, int pageSize, String sortBy, String sortOrder) {
        boolean doHighlight = true;
        if(queryString.isEmpty() && sortBy.isEmpty()) {
            sortBy = Fields.RELEASE_DATE; // TODO: Make sure RELEASE_TIME is used correctly all over the code base
            doHighlight = false;
        } else if(sortBy.isEmpty()) {
            sortBy = RELEVANCE;
        }
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode response = mapper.createObjectNode();
        JsonNode selectedFields = selectedFacetsAndFields==null? null : selectedFacetsAndFields.get("fields");
        JsonNode selectedFacets = selectedFacetsAndFields==null? null : selectedFacetsAndFields.get("facets");
        Pair<Query, EFOExpansionTerms> resultPair = queryService.makeQuery(queryString, prjName, selectedFields);
        try {
            Query queryAfterSecurity = resultPair.getKey();
            if(selectedFacets!=null){
                queryAfterSecurity = facetService.applyFacets(queryAfterSecurity, selectedFacets);
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
    public InputStreamResource getStudyAsStream(String accession, String relativePath) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(indexConfig.getFileRootDir() + "/"+ relativePath + "/"+accession+".json");
        return new InputStreamResource(fileInputStream);
    }

    @Override
    public String getFieldStats() throws Exception {
        String responseString = statsCache.getIfPresent(STATS_ENDPOINT);
        if (responseString == null) {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode response = mapper.createObjectNode();
            IndexSearcher searcher = indexManager.getIndexSearcher();

            DocValuesStats.SortedLongDocValuesStats  fileStats = new DocValuesStats.SortedLongDocValuesStats ("files");
            searcher.search(new MatchAllDocsQuery(), new DocValuesStatsCollector(fileStats));
            response.put("files", fileStats.sum());

            DocValuesStats.SortedLongDocValuesStats  linkStats = new DocValuesStats.SortedLongDocValuesStats ("links");
            searcher.search(new MatchAllDocsQuery(), new DocValuesStatsCollector(linkStats));
            response.put("links", linkStats.sum());

            indexManager.getIndexWriter().getLiveCommitData().forEach(entry -> {
                if (entry.getKey().equalsIgnoreCase("@endTimeTS")) {
                    response.put("time", Long.parseLong(entry.getValue()) );
                }
            });
            responseString = response.toString();
            statsCache.put(STATS_ENDPOINT, responseString);
        }
        return responseString;
    }

    @Override
    public ObjectNode getSimilarStudies(String accession, String secretKey) throws Exception {
        int maxHits = 4;
        MoreLikeThis mlt = new MoreLikeThis(indexManager.getIndexReader());
        mlt.setFieldNames(new String[]{Fields.CONTENT, Fields.TITLE, Facets.PROJECT});
        mlt.setAnalyzer(analyzerManager.getPerFieldAnalyzerWrapper());
        ObjectMapper mapper = new ObjectMapper();
        Integer docNumber = getDocumentNumberByAccession(accession, secretKey);
        Query likeQuery = mlt.like( docNumber);
        TopDocs mltDocs = indexManager.getIndexSearcher().search( likeQuery , maxHits);
        ArrayNode similarStudies = mapper.createArrayNode();
        for (int i = 1; i < mltDocs.scoreDocs.length; i++) {
            ObjectNode study = mapper.createObjectNode();
            study.set(Fields.ACCESSION, mapper.valueToTree(indexManager.getIndexReader().document(mltDocs.scoreDocs[i].doc).get(Fields.ACCESSION)));
            study.set(Fields.TITLE, mapper.valueToTree(indexManager.getIndexReader().document(mltDocs.scoreDocs[i].doc).get(Fields.TITLE)));
            similarStudies.add(study);
        }
        ObjectNode result = mapper.createObjectNode();
        if (mltDocs.totalHits.value>1) {
            result.set ("similarStudies", similarStudies);
        }
        return result;
    }

    @Override
    public Document getDocumentByAccession(String accession, String secretKey) {
        Integer docNumber = getDocumentNumberByAccession(accession, secretKey);
        try {
            return indexManager.getIndexReader().document(docNumber);
        } catch (IOException ex) {
            logger.error("Problem retrieving "+ accession, ex);
        }
        return null;
    }

    private Integer getDocumentNumberByAccession(String accession, String secretKey){
        QueryParser parser = new QueryParser(Fields.ACCESSION, new AttributeFieldAnalyzer());
        parser.setSplitOnWhitespace(true);
        Query query = null;
        try {
            query = parser.parse(Fields.ACCESSION+":"+accession);
            Query result = securityQueryBuilder.applySecurity(query, secretKey);
            TopDocs topDocs = indexManager.getIndexSearcher().search(result, 1);
            if(topDocs.totalHits.value==1) {
                return topDocs.scoreDocs[0].doc;
            }
        } catch (Throwable ex){
            logger.error("Problem in checking security", ex);
        }
        return null;
    }

    public String getLatestStudies() throws Exception {
        String responseString = statsCache.getIfPresent(LATEST_ENDPOINT);
        if (responseString == null) {
            responseString = search(URLDecoder.decode(Constants.Fields.TYPE + ":study", String.valueOf(UTF_8)), null, null, 1, 5, Constants.Fields.RELEASE_TIME, Constants.SortOrder.DESCENDING);
            statsCache.put(LATEST_ENDPOINT, responseString);
        }
        return responseString;
    }
}
