package uk.ac.ebi.biostudies.service.impl;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.document.*;
import org.apache.lucene.facet.FacetField;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexableField;
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
    public void indexAll(String fileName) {
        Long startTime = System.currentTimeMillis();
        ExecutorService executorService = new ThreadPoolExecutor(indexConfig.getThreadCount(), indexConfig.getThreadCount(),
                60, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(indexConfig.getQueueSize()), new ThreadPoolExecutor.CallerRunsPolicy());
        String inputStudiesFile = System.getProperty("java.io.tmpdir")+"/";
        if(fileName!=null && !fileName.isEmpty())
            inputStudiesFile = inputStudiesFile +fileName;
        else
            inputStudiesFile = inputStudiesFile + Constants.STUDIES_JSON_FILE;
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
                executorService.execute(new JsonDocumentIndexer(submission, taxonomyManager, indexManager));
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
        QueryParser parser = new QueryParser(Constants.ACCESSION, new AttributeFieldAnalyzer());
        String strquery = Constants.ACCESSION+":"+accession;
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
                sourceLocation = sourceLocation.replaceAll(Constants.STUDIES_JSON_FILE, jsonFileName);
            else
                jsonFileName = Constants.STUDIES_JSON_FILE;
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
        IndexManager indexManager;

        public JsonDocumentIndexer(JsonNode json,TaxonomyManager taxonomyManager, IndexManager indexManager) {
            this.writer = indexManager.getIndexWriter();
            this.json = json;
            this.taxonomyManager = taxonomyManager;
            this.indexManager = indexManager;
        }

        @Override
        public void run() {
            Map<String, Object> valueMap = new HashMap<>();
            try {
                valueMap.put( Constants.ID, json.get("accno").textValue() );
                valueMap.put( Constants.SECRET_KEY, json.has("seckey") ? json.get("seckey").textValue() : null );
                valueMap.put( Constants.ACCESSION, valueMap.get(Constants.ID));
                valueMap.put( Constants.TYPE, json.get("section").get("type").textValue().toLowerCase());
                valueMap.put( Constants.TITLE, getTitle(json, (String)valueMap.get(Constants.ACCESSION)));

                valueMap.put( Constants.FILES, json.findValues("files").stream().mapToLong(
                        jsonNode -> jsonNode.findValues("path").size()
                        ).sum()
                );
                StringBuilder content = new StringBuilder(String.join(" ", json.findValuesAsText("value")));
                content.append(" ");
                content.append(String.join(" ", json.findValuesAsText("accno")));
                content.append(" ");
                content.append(json.findValues("files").stream().map(jsonNode -> jsonNode.findValuesAsText("path").stream().collect(Collectors.joining(" "))).collect(Collectors.joining(" ")));
                content.append(" ");
                content.append(json.findValues("links").stream().map(jsonNode -> jsonNode.findValuesAsText("url").stream().collect(Collectors.joining(" "))).collect(Collectors.joining(" ")));
                valueMap.put( Constants.CONTENT, content.toString());

                String linkType = json.findValues("links")
                        .stream()
                        .flatMap ( jsonNode ->  StreamSupport.stream(jsonNode.spliterator(), false).flatMap( j-> StreamSupport.stream(j.spliterator(), false)) )
                        .map( link-> {
                            return link.findParents("name").stream().map( a-> {
                                return (a.get("name").textValue().equalsIgnoreCase("Type")) ? a.get("value").textValue() : "";
                            }).collect(Collectors.joining(" "));

                        }).collect(Collectors.joining(" "));
                valueMap.put(Constants.LINK_TYPE, linkType.isEmpty() ? null : linkType );
                valueMap.put( Constants.LINKS, json.findValues("links").stream().mapToLong(
                        jsonNode -> jsonNode.findValues("url").size()
                        ).sum()
                );
                String author = "";
                if(json.get("section").has("subsections")) {
                    author = StreamSupport.stream(json.get("section").get("subsections").spliterator(), false)
                            .filter(jsonNode ->
                                    jsonNode.has("type") && jsonNode.get("type").textValue().equalsIgnoreCase("Author") && jsonNode.get("attributes").isArray() && jsonNode.get("attributes").get(0).get("name").asText().equalsIgnoreCase("Name"))
                            .map(authorNode -> authorNode.get("attributes").get(0).get("value").asText()).collect(Collectors.joining(", "));
                }
                valueMap.put( Constants.AUTHORS, author);

                String access = !json.has("accessTags") ? "" :
                        StreamSupport.stream(json.get("accessTags").spliterator(),false)
                                .map( s-> s.textValue())
                                .collect(Collectors.joining(" "));
                valueMap.put( Constants.ACCESS, access.replaceAll("~", ""));

                String value="";

                long releaseDateLong = 0L;
                long creationDateLong = 0L;

                if(json.has(Constants.CREATION_TIME))
                    creationDateLong = Long.valueOf(json.get(Constants.CREATION_TIME).asText())*1000;
                valueMap.put( Constants.CREATION_TIME, DateTools.timeToString(creationDateLong, DateTools.Resolution.DAY));

                if(json.has("rtime"))
                    releaseDateLong = Long.valueOf(json.get("rtime").asText())*1000;
                valueMap.put(Constants.RDATE_LONG, releaseDateLong);
                if(releaseDateLong==0L && !String.valueOf(valueMap.get(Constants.ACCESS)).contains("public")) {
                        releaseDateLong = 2524664024570L; // 2015-01-01
                }
                valueMap.put(Constants.RELEASE_DATE, DateTools.timeToString(releaseDateLong, DateTools.Resolution.DAY));
                valueMap.put(Constants.YEAR, releaseDateLong==2524664024570L ? null :  DateTools.timeToString(releaseDateLong, DateTools.Resolution.YEAR));
                String project = "";
                if(json.has("attributes")) {
                    project = StreamSupport.stream(json.get("attributes").spliterator(), false)
                            .filter(jsonNode ->
                                    jsonNode.has("name") && jsonNode.get("name").textValue().equalsIgnoreCase("attachto"))
                            .map(s -> s.get("value").textValue())
                            .collect(Collectors.joining(","));
                }
                valueMap.put(Constants.PROJECT, project);
                ReadContext jsonPathContext = null;
                for(JsonNode fieldMetadataNode:indexManager.indexDetails.findValue("public")){
                    if( fieldMetadataNode.has("jpath") &&  !fieldMetadataNode.get("jpath").asText().isEmpty()){
                        extractWithJsonPath(jsonPathContext, json, valueMap, fieldMetadataNode);
                    }
                }

                //extract facets
                if(indexManager.indexDetails.findValue(project.toLowerCase())!=null && json.has("section") && json.get("section").has("attributes")) {
                    JsonNode attNodes = json.get("section").get("attributes");
                    for(JsonNode fieldMetadataNode:indexManager.indexDetails.findValue(project.toLowerCase())){
                            if( !fieldMetadataNode.has("jpath") || fieldMetadataNode.get("jpath").asText().isEmpty()){
                                value = StreamSupport.stream(attNodes.spliterator(), false)
                                        .filter(jsonNode ->
                                                jsonNode.has("name") && jsonNode.get("name").textValue().equalsIgnoreCase(fieldMetadataNode.get("title").asText()))
                                        .map(s->s.get("value").textValue() )
                                        .collect(Collectors.joining("£"));
                                valueMap.put(fieldMetadataNode.get("name").asText(), value);
                            }
                            else{
                                extractWithJsonPath(jsonPathContext, json, valueMap, fieldMetadataNode);
                            }

                    }
                }


                updateDocument(valueMap);
            } catch (Exception e) {
                logger.error("problem in indexing accession {}", json.get("accno").textValue(), e );
            }
        }

        private void extractWithJsonPath(ReadContext jsonPathContext, JsonNode json, Map<String, Object> valueMap, JsonNode fieldMetadataNode){
            String result="n/a";
            try {
                if (jsonPathContext == null)
                    jsonPathContext = JsonPath.parse(json.toString());
                try {
                    List<String> resultData = jsonPathContext.read(fieldMetadataNode.get("jpath").asText());
                    result = String.join(",", resultData);
                } catch (ClassCastException e) {
                    result = jsonPathContext.read(json.get("jpath").asText());
                }
            }catch (NullPointerException e){
                //it means this document has no value for this field so do nothing
            }
            valueMap.put(fieldMetadataNode.get("name").asText(), result);
        }

        private void updateDocument(Map<String, Object> valueMap) throws IOException {
            Document doc = new Document();

            //TODO: replace by classes if possible
            String value;
            String prjName = (String)valueMap.get("project");
            for (String field: indexManager.getProjectRelatedFields(prjName.toLowerCase())) {
                JsonNode curNode = indexManager.getAllValidFields().get(field);
                String fieldType = curNode.get("fieldType").asText();
                try{
                    switch (fieldType) {
                        case "str_tokenized":
                            value = String.valueOf(valueMap.get(field));
                            doc.add(new TextField(String.valueOf(field), value, Field.Store.YES));
                            break;
                        case "str_untokenized":
                            value = String.valueOf(valueMap.get(field));
                            Field unTokenizeField = new Field(String.valueOf(field), value, TYPE_NOT_ANALYZED);
                            doc.add(unTokenizeField);
                            if(curNode.has ("isSortable") && curNode.get("isSortable").textValue().equalsIgnoreCase("true"))
                                doc.add( new SortedDocValuesField(String.valueOf(field), new BytesRef( valueMap.get(field).toString())));
                            break;
                        case "long":
                            doc.add(new SortedNumericDocValuesField(String.valueOf(field), (Long) valueMap.get(field)));
                            doc.add(new StoredField(String.valueOf(field), valueMap.get(field).toString()));
                            break;
                        case "facet":
                            addFacet(String.valueOf(valueMap.get(field)), field, doc);
                    }
                }catch(Exception ex){
                    logger.error("field name: {} doc accession: {}", field.toString(), String.valueOf(valueMap.get(Constants.ACCESSION)), ex);
                }


            }

            Document facetedDocument = taxonomyManager.getFacetsConfig().build(taxonomyManager.getTaxonomyWriter() ,doc);
            writer.updateDocument(new Term(Constants.ID, valueMap.get(Constants.ACCESSION).toString()), facetedDocument);

        }

        private void addFacet(String value, String fieldName, Document doc){
            if(value==null || value.isEmpty()) {
                value = "n/a";
            }
            for(String subVal:value.split("£")) {
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
                logger.error("title is empty accession: {0}", accession);
            return title;
        }
    }

}