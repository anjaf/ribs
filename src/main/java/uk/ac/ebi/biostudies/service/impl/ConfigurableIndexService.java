package uk.ac.ebi.biostudies.service.impl;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.document.*;
import org.apache.lucene.facet.FacetField;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.BytesRef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import uk.ac.ebi.biostudies.api.util.Constants;
import uk.ac.ebi.biostudies.api.util.analyzer.AttributeFieldAnalyzer;
import uk.ac.ebi.biostudies.config.IndexConfig;
import uk.ac.ebi.biostudies.config.TaxonomyManager;
import uk.ac.ebi.biostudies.schedule.jobs.ReloadOntologyJob;
import uk.ac.ebi.biostudies.service.FacetService;
import uk.ac.ebi.biostudies.service.IndexService;
import uk.ac.ebi.biostudies.config.IndexManager;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.apache.commons.lang.StringUtils.isNotBlank;
import static uk.ac.ebi.biostudies.api.util.Constants.*;

/**
 * Created by ehsan on 27/02/2017.
 */


@Service
@Scope("singleton")

public class ConfigurableIndexService implements IndexService {

    public static final FieldType TYPE_NOT_ANALYZED = new FieldType();
    static {
        TYPE_NOT_ANALYZED.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS);
        TYPE_NOT_ANALYZED.setTokenized(false);
        TYPE_NOT_ANALYZED.setStored(true);
    }

    private Logger logger = LogManager.getLogger(ConfigurableIndexService.class.getName());

    @Autowired
    IndexConfig indexConfig;

    @Autowired
    IndexManager indexManager;

    @Autowired
    TaxonomyManager taxonomyManager;

    @Autowired
    FacetService facetService;

    @Autowired
    ReloadOntologyJob reloadOntologyJob;

    @PostConstruct
    public void init(){
        reloadOntologyJob.doExecute();
    }


    @Override
    public void indexAll(String fileName, boolean removeFileDocuments) {
        Long startTime = System.currentTimeMillis();
        ExecutorService executorService = new ThreadPoolExecutor(indexConfig.getThreadCount(), indexConfig.getThreadCount(),
                60, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(indexConfig.getQueueSize()), new ThreadPoolExecutor.CallerRunsPolicy());
        String inputStudiesFile = System.getProperty("java.io.tmpdir")+"/";
        if(fileName!=null && !fileName.isEmpty())
            inputStudiesFile = inputStudiesFile +fileName;
        else
            inputStudiesFile = inputStudiesFile + STUDIES_JSON_FILE;
        int counter = 0;
        try (InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(inputStudiesFile), "UTF-8")) {
            JsonFactory factory = new JsonFactory();
            JsonParser parser = factory.createParser(inputStreamReader);


            JsonToken token = parser.nextToken();
            while (!JsonToken.START_ARRAY.equals(token)) {
                token = parser.nextToken();
            }

            ObjectMapper mapper = new ObjectMapper();
            while (true) {
                token = parser.nextToken();
                if (!JsonToken.START_OBJECT.equals(token)) {
                    break;
                }
                if (token == null) {
                    break;
                }

                JsonNode submission = mapper.readTree(parser);
                executorService.execute(new JsonDocumentIndexer(submission, taxonomyManager, indexManager, removeFileDocuments));
                if(++counter % 10000==0) {
                    logger.info("{} docs indexed", counter);
                }
            }
            Map<String,String> commitData = new HashMap<>();
            while(token!=JsonToken.END_OBJECT){
                if(token.name().equalsIgnoreCase("field_name")) {
                    String key = parser.getText();
                    token = parser.nextToken();
                    commitData.put(key,token.isNumeric() ? Long.toString(parser.getLongValue()) : parser.getText());

                }
                token = parser.nextToken();
            }

            indexManager.getIndexWriter().setLiveCommitData(commitData.entrySet());
            executorService.shutdown();
            executorService.awaitTermination(5, TimeUnit.HOURS);
            taxonomyManager.commitTaxonomy();
            indexManager.getIndexWriter().commit();
            indexManager.refreshIndexSearcherAndReader();
            taxonomyManager.refreshTaxonomyReader();
            logger.info("indexing lasted {} seconds", (System.currentTimeMillis()-startTime)/1000);
        }
        catch (Throwable error){
            logger.error("problem in parsing "+ fileName , error);
        }
    }


    @Override
    public void deleteDoc(String accession) throws Exception{
        if(accession==null || accession.isEmpty())
            return;
        QueryParser parser = new QueryParser(Fields.ACCESSION, new AttributeFieldAnalyzer());
        String strquery = Fields.ACCESSION+":"+accession;
        parser.setSplitOnWhitespace(true);
        Query query = parser.parse(strquery);
        indexManager.getIndexWriter().deleteDocuments(query);
        indexManager.getIndexWriter().commit();
        indexManager.refreshIndexSearcherAndReader();
    }

    @Override
    public void clearIndex(boolean commit) throws IOException {
        indexManager.getIndexWriter().deleteAll();
        indexManager.getIndexWriter().forceMergeDeletes();
        if(commit) {
            indexManager.getIndexWriter().commit();
            indexManager.refreshIndexSearcherAndReader();
        }
    }

    @Override
    public synchronized void copySourceFile(String jsonFileName) throws IOException {
        String sourceLocation = indexConfig.getStudiesInputFile();
        if (isNotBlank(sourceLocation)) {
            if (jsonFileName != null && !jsonFileName.isEmpty())
                sourceLocation = sourceLocation.replaceAll(STUDIES_JSON_FILE, jsonFileName);
            else
                jsonFileName = STUDIES_JSON_FILE;
            File srcFile = new File(sourceLocation);
            File destFile = new File(System.getProperty("java.io.tmpdir"), jsonFileName);
            logger.info("Making a local copy  of {} at {}", srcFile.getAbsolutePath(), destFile.getAbsolutePath());
            com.google.common.io.Files.copy(srcFile, destFile);
        }
    }


    public static class JsonDocumentIndexer implements Runnable {
        private Logger logger = LogManager.getLogger(JsonDocumentIndexer.class.getName());

        private IndexWriter writer;
        private JsonNode json;
        private TaxonomyManager taxonomyManager;
        private IndexManager indexManager;
        private boolean removeFileDocuments;

        public JsonDocumentIndexer(JsonNode json,TaxonomyManager taxonomyManager, IndexManager indexManager, boolean removeFileDocuments) {
            this.writer = indexManager.getIndexWriter();
            this.json = json;
            this.taxonomyManager = taxonomyManager;
            this.indexManager = indexManager;
            this.removeFileDocuments = removeFileDocuments;
        }

        @Override
        public void run() {
            Map<String, Object> valueMap = new HashMap<>();
            try {
                valueMap.put( Fields.ID, json.get("accno").textValue() );
                valueMap.put( Fields.SECRET_KEY, json.has("seckey") ? json.get("seckey").textValue() : null );
                valueMap.put( Fields.ACCESSION, valueMap.get(Fields.ID));
                valueMap.put( Fields.TYPE, json.get("section").get("type").textValue().toLowerCase());
                valueMap.put( Fields.TITLE, getTitle(json, (String)valueMap.get(Fields.ACCESSION)));
                valueMap.put( Fields.FILES, json.findValues("files").stream().mapToLong(
                        jsonNode -> jsonNode.findValues("path").size()
                        ).sum()
                );
                valueMap.put( Fields.LINKS, json.findValues("links").stream().mapToLong(
                        jsonNode -> jsonNode.findValues("url").size()
                        ).sum()
                );
                String access = !json.has("accessTags") ? "" :
                        StreamSupport.stream(json.get("accessTags").spliterator(),false)
                                .map( s-> s.textValue())
                                .collect(Collectors.joining(" "));
                valueMap.put( Fields.ACCESS, access.replaceAll("~", ""));


                String project = "";
                if(json.has("attributes")) {
                    project = StreamSupport.stream(json.get("attributes").spliterator(), false)
                            .filter(jsonNode ->
                                    jsonNode.has("name") && jsonNode.get("name").textValue().equalsIgnoreCase("attachto"))
                            .map(s -> s.get("value").textValue())
                            .collect(Collectors.joining(","));
                }
                valueMap.put(Facets.PROJECT, project);
                Set<String> columnSet = new LinkedHashSet<>();
                if(valueMap.get(Fields.TYPE).toString().equalsIgnoreCase("study")) {
                    String sectionsWithFiles = FileIndexer.indexSubmissionFiles((String) valueMap.get(Fields.ACCESSION), json, writer, columnSet, removeFileDocuments);
                    if (sectionsWithFiles !=null ) {
                        valueMap.put(Fields.SECTIONS_WITH_FILES, sectionsWithFiles);
                    }
                }
                valueMap.put(Constants.File.FILE_ATTS, columnSet);
                extractContent(valueMap);
                extractAuthorData(valueMap);
                extractDates(valueMap);
                extractDynamicFieldsAndFacets(valueMap);
                updateDocument(valueMap);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("problem in indexing accession {}", json.get("accno").textValue(), e );
            }
        }

        private void extractAuthorData(Map<String, Object> valueMap) {
            // extract authors and orcids
            List<String> authors = new ArrayList<>();
            List<String> orcids = new ArrayList<>();
            if(json.get("section").has("subsections")) {
                StreamSupport.stream(json.get("section").get("subsections").spliterator(), false)
                        .filter(jsonNode -> jsonNode.has("type")
                                            && jsonNode.get("type").textValue().equalsIgnoreCase("Author")
                                            && jsonNode.has("attributes")
                                            && jsonNode.get("attributes").isArray())
                        .forEach( section-> {
                            section.get("attributes").forEach(attr-> {
                                String name = attr.get("name").asText();
                                String value = attr.get("value").asText();
                                if (name.equalsIgnoreCase("Name")) {
                                    authors.add(value);
                                } else if (name.equalsIgnoreCase("ORCID")) {
                                    orcids.add(value);
                                }
                            });
                        }
                );
            }
            valueMap.put( Fields.AUTHOR, StringUtils.join(authors," "));
            valueMap.put( Fields.ORCID, StringUtils.join(orcids," "));
        }

        private void extractContent(Map<String, Object> valueMap) {
            String accession = json.get("accno").textValue();
            StringBuilder content = new StringBuilder(String.join(" ", accession));
            content.append(" ");
            if (accession.startsWith("S-EPMC")) {// hack to make sure we are indexing PMC accessions in full text
                content.append( accession.substring(3) ).append(" ");
            }
            content.append(String.join(" ", json.get("section").findValuesAsText("value")));
            content.append(" ");
            content.append(json.findValues("files").stream().map(jsonNode -> jsonNode.findValuesAsText("path").stream().collect(Collectors.joining(" "))).collect(Collectors.joining(" ")));
            content.append(" ");
            content.append(json.findValues("links").stream().map(jsonNode -> jsonNode.findValuesAsText("url").stream().collect(Collectors.joining(" "))).collect(Collectors.joining(" ")));
            valueMap.put( Fields.CONTENT, content.toString());
        }

        private void extractDates(Map<String, Object> valueMap) {
            long releaseDateLong = 0L;
            long creationDateLong = 0L;

            if(json.has(Fields.CREATION_TIME)) {
                creationDateLong = Long.valueOf(json.get(Fields.CREATION_TIME).asText()) * 1000;
            }
            valueMap.put(Fields.CREATION_TIME, creationDateLong);

            if(json.has(Fields.MODIFICATION_TIME)) {
                long modificationTimeLong = Long.valueOf(json.get(Fields.MODIFICATION_TIME).asText()) * 1000;
                valueMap.put(Fields.MODIFICATION_TIME, modificationTimeLong);
                valueMap.put(Facets.MODIFICATION_YEAR_FACET, DateTools.timeToString(modificationTimeLong, DateTools.Resolution.YEAR));
            }


            if(json.has(Fields.RELEASE_TIME) && !json.get(Fields.RELEASE_TIME).asText().equals("-1")) {
                releaseDateLong = Long.valueOf(json.get(Fields.RELEASE_TIME).asText()) * 1000;
            }
            if(releaseDateLong==0L && !String.valueOf(valueMap.get(Fields.ACCESS)).contains(PUBLIC)) {
                    releaseDateLong = Long.MAX_VALUE;
            }
            valueMap.put(Fields.RELEASE_TIME, releaseDateLong);
            valueMap.put(RELEASE_DATE, DateTools.timeToString(releaseDateLong, DateTools.Resolution.DAY));
            valueMap.put(Facets.RELEASED_YEAR_FACET, (releaseDateLong==Long.MAX_VALUE || releaseDateLong==0) ? NA :  DateTools.timeToString(releaseDateLong, DateTools.Resolution.YEAR));
        }

        // extracts facets. Assumes project is already in the valueMap
        private void extractDynamicFieldsAndFacets(Map<String, Object> valueMap) {
            String project = valueMap.get(Facets.PROJECT).toString();

            ReadContext jsonPathContext = null;
            for(JsonNode fieldMetadataNode:indexManager.indexDetails.findValue(PUBLIC)){
                if( fieldMetadataNode.has(IndexEntryAttributes.JSON_PATH) &&  !fieldMetadataNode.get(IndexEntryAttributes.JSON_PATH).asText().isEmpty()){
                    extractWithJsonPath(jsonPathContext, json, valueMap, fieldMetadataNode);
                }
            }

            //extract facets and fields
            if(indexManager.indexDetails.findValue(project.toLowerCase())!=null && json.has("section") && json.get("section").has("attributes")) {
                JsonNode attNodes = json.get("section").get("attributes");
                for(JsonNode fieldMetadataNode:indexManager.indexDetails.findValue(project.toLowerCase())){
                        if( !fieldMetadataNode.has(IndexEntryAttributes.JSON_PATH) || fieldMetadataNode.get(IndexEntryAttributes.JSON_PATH).asText().isEmpty()){
                            String value = StreamSupport.stream(attNodes.spliterator(), false)
                                    .filter(jsonNode ->
                                            jsonNode.has("name") && jsonNode.get("name").textValue().equalsIgnoreCase(fieldMetadataNode.get(IndexEntryAttributes.TITLE).asText()))
                                    .map(s->s.get("value").textValue() )
                                    .collect( Collectors.joining( fieldMetadataNode.get(IndexEntryAttributes.FIELD_TYPE).asText().equalsIgnoreCase(IndexEntryAttributes.FieldTypeValues.FACET)
                                            ?  Facets.DELIMITER : " "));
                            //if (StringUtils.isNotBlank(value)) {
                                valueMap.put(fieldMetadataNode.get(IndexEntryAttributes.NAME).asText(), value);
                            //}
                        }
                        else{
                            extractWithJsonPath(jsonPathContext, json, valueMap, fieldMetadataNode);
                        }

                }
            }
            // extract file type facet (which needs further processing to get the extension out)
            if (jsonPathContext == null) {
                jsonPathContext = JsonPath.parse(json.toString());
                valueMap.put(Facets.FILE_TYPE, ((List<String>) jsonPathContext.read("$..files.*.path")).stream()
                        .map(s -> {
                            if (s == null) return NA;
                            int k = s.lastIndexOf(".");
                            return k >= 0 ? s.substring(s.lastIndexOf(".") + 1) : NA;
                        }).collect(Collectors.joining(Facets.DELIMITER))
                );
            }
        }

        private void extractWithJsonPath(ReadContext jsonPathContext, JsonNode json, Map<String, Object> valueMap, JsonNode fieldMetadataNode){
            Object result= NA;
            try {
                if (jsonPathContext == null)
                    jsonPathContext = JsonPath.parse(json.toString());
                List resultData = null;
                try {
                    resultData = jsonPathContext.read(fieldMetadataNode.get(IndexEntryAttributes.JSON_PATH).asText());

                    switch (fieldMetadataNode.get(IndexEntryAttributes.FIELD_TYPE).asText()) {
                        case IndexEntryAttributes.FieldTypeValues.FACET:
                            result =  String.join (Facets.DELIMITER, resultData);
                            break;
                        case IndexEntryAttributes.FieldTypeValues.LONG:
                            result = resultData.stream().collect(Collectors.counting());
                            break;
                        default:
                            result =  String.join (" ", resultData);
                            break;
                    }

                } catch (ClassCastException e) {
                    result = jsonPathContext.read(json.get(IndexEntryAttributes.JSON_PATH).asText());
                }
            } catch (NullPointerException e){
                //it means this document has no value for this field so do nothing
            }
            valueMap.put(fieldMetadataNode.get(IndexEntryAttributes.NAME).asText(), result);
        }

        private void updateDocument(Map<String, Object> valueMap) throws IOException {
            Document doc = new Document();

            //TODO: replace by classes if possible
            String value;
            String prjName = (String)valueMap.get(Facets.PROJECT);
            addFileAttributes(doc, (Set<String>) valueMap.get(Constants.File.FILE_ATTS));
            for (String field: indexManager.getProjectRelatedFields(prjName.toLowerCase())) {
                JsonNode curNode = indexManager.getAllValidFields().get(field);
                String fieldType = curNode.get(IndexEntryAttributes.FIELD_TYPE).asText();
                try{
                    switch (fieldType) {
                        case IndexEntryAttributes.FieldTypeValues.TOKENIZED_STRING:
                            value = String.valueOf(valueMap.get(field));
                            doc.add(new TextField(String.valueOf(field), value, Field.Store.YES));
                            break;
                        case IndexEntryAttributes.FieldTypeValues.UNTOKENIZED_STRING:
                            if (!valueMap.containsKey(field)) break;
                            value = String.valueOf(valueMap.get(field));
                            Field unTokenizeField = new Field(String.valueOf(field), value, TYPE_NOT_ANALYZED);
                            doc.add(unTokenizeField);
                            if(curNode.has (IndexEntryAttributes.SORTABLE) && curNode.get(IndexEntryAttributes.SORTABLE).asBoolean(false))
                                doc.add( new SortedDocValuesField(String.valueOf(field), new BytesRef( valueMap.get(field).toString())));
                            break;
                        case IndexEntryAttributes.FieldTypeValues.LONG:
                            if (!valueMap.containsKey(field) || StringUtils.isEmpty(valueMap.get(field).toString()) ) break;
                            doc.add(new SortedNumericDocValuesField(String.valueOf(field), (Long) valueMap.get(field)));
                            doc.add(new StoredField(String.valueOf(field), valueMap.get(field).toString()));
                            doc.add( new LongPoint(String.valueOf(field), (Long) valueMap.get(field)  ));
                            break;
                        case IndexEntryAttributes.FieldTypeValues.FACET:
                            addFacet(String.valueOf(valueMap.get(field)), field, doc, curNode);
                    }
                }catch(Exception ex){
                    logger.error("field name: {} doc accession: {}", field.toString(), String.valueOf(valueMap.get(Fields.ACCESSION)), ex);
                }


            }

            Document facetedDocument = taxonomyManager.getFacetsConfig().build(taxonomyManager.getTaxonomyWriter() ,doc);
            writer.updateDocument(new Term(Fields.ID, valueMap.get(Fields.ACCESSION).toString()), facetedDocument);

        }

        private void addFileAttributes(Document doc, Set<String> columnAtts){
            StringBuilder allAtts = new StringBuilder("Name|Size|");
            for(String att:columnAtts)
                allAtts.append(att).append("|");
            doc.add(new StringField(Constants.File.FILE_ATTS, allAtts.toString(),Field.Store.YES));
        }

        private void addFacet(String value, String fieldName, Document doc, JsonNode facetConfig){
            if(value==null || value.isEmpty()) {
                value = NA;
            }
            for(String subVal: org.apache.commons.lang3.StringUtils.split(value, Facets.DELIMITER)) {
                if(subVal.equalsIgnoreCase(NA) && facetConfig.has(IndexEntryAttributes.DEFAULT_VALUE)){
                    subVal = facetConfig.get(IndexEntryAttributes.DEFAULT_VALUE).textValue();
                }
                doc.add(new FacetField(fieldName, subVal.trim().toLowerCase()));
            }
        }

        private String getTitle(JsonNode json, String accession) {
            String title = "";

            try {
                title = StreamSupport.stream(this.json.get("section").get("attributes").spliterator(), false)
                        .filter(jsonNode -> jsonNode.get("name").textValue().equalsIgnoreCase("Title"))
                        .findFirst().get().get("value").textValue().trim();
            } catch (Exception ex1) {
                logger.debug( "Title not found. Trying submission title for " + accession);
                try {
                    title = StreamSupport.stream(this.json.get("attributes").spliterator(), false)
                            .filter(jsonNode -> jsonNode.get("name").textValue().equalsIgnoreCase("Title"))
                            .map(jsonNode -> jsonNode.findValue("value").asText().trim())
                            .collect(Collectors.joining(","));//get().get("value").textValue().trim();
                } catch ( Exception ex2) {
                    logger.error("Title not found for " + json.toString().substring(0,100));
                }
            }
            if(title.isEmpty())
                logger.error("title is empty accession: {}", accession);
            return title;
        }
    }

}
