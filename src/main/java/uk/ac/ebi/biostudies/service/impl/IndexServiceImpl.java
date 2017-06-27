package uk.ac.ebi.biostudies.service.impl;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.document.*;
import org.apache.lucene.facet.FacetField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import uk.ac.ebi.biostudies.api.BioStudiesField;
import uk.ac.ebi.biostudies.api.BioStudiesFieldType;
import uk.ac.ebi.biostudies.api.util.Constants;
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
public class IndexServiceImpl implements IndexService {

    private Logger logger = LogManager.getLogger(IndexServiceImpl.class.getName());

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
//            inputStudiesFile = indexConfig.getStudiesFileDirectory()+fileName;
        else
            inputStudiesFile = inputStudiesFile + Constants.STUDIES_JSON_FILE;
//            inputStudiesFile = indexConfig.getStudiesInputFile();
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
                executorService.execute(new JsonDocumentIndexer(submission, indexManager.getIndexWriter(), taxonomyManager));
                if(++counter%1000==0)
                    logger.info("{} docs indexed", counter);
            }

            executorService.shutdown();
            executorService.awaitTermination(5, TimeUnit.HOURS);
            taxonomyManager.commitTaxonomy();
            indexManager.getIndexWriter().commit();
            indexManager.refreshIndexSearcherAndReader();
            taxonomyManager.refreshTaxonomyReader();
            logger.info("indexing lasted {} seconds", (System.currentTimeMillis()-startTime)/1000);
//            jnode=facetService.getHecatosFacets();
//            logger.debug(j JsonNodenode.toString());
        }
        catch (Throwable error){
            logger.error("problem in parsing biostudies.json file", error);
        }
    }


    @Override
    public void deleteDoc(String accession) throws Exception{
        if(accession==null || accession.isEmpty())
            return;
        QueryParser parser = new QueryParser(BioStudiesField.ACCESSION.toString(), BioStudiesField.ACCESSION.getAnalyzer());
        String strquery = BioStudiesField.ACCESSION.toString()+":"+accession;
        Query query = parser.parse(strquery);
        indexManager.getIndexWriter().deleteDocuments(query);
        indexManager.getIndexWriter().commit();
    }

    @Override
    public void clearIndex(boolean commit) throws IOException {
        indexManager.getIndexWriter().deleteAll();
        indexManager.getIndexWriter().forceMergeDeletes();
        if(commit)
            indexManager.getIndexWriter().commit();

    }

    @Override
    public synchronized void copySourceFile(String jsonFileName) throws IOException {
        String sourceLocation = indexConfig.getStudiesInputFile();
        if (isNotBlank(sourceLocation)) {
            if (jsonFileName != null && !jsonFileName.isEmpty())
                sourceLocation = sourceLocation.replaceAll(Constants.STUDIES_JSON_FILE, jsonFileName);
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

        public JsonDocumentIndexer(JsonNode json, IndexWriter writer, TaxonomyManager taxonomyManager) {

            this.writer = writer;
            this.json = json;
            this.taxonomyManager = taxonomyManager;
        }

        @Override
        public void run() {
            try {
                Map<BioStudiesField, Object> valueMap = new HashMap<>(BioStudiesField.values().length);
                valueMap.put( BioStudiesField.ID, json.get("accno").textValue() );
                valueMap.put( BioStudiesField.ACCESSION, valueMap.get(BioStudiesField.ID));
                valueMap.put( BioStudiesField.TYPE, json.get("section").get("type").textValue().toLowerCase());
                valueMap.put( BioStudiesField.TITLE, getTitle(json));

                valueMap.put( BioStudiesField.FILES, json.findValues("files").stream().mapToLong(
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
                valueMap.put( BioStudiesField.CONTENT, content.toString());
                valueMap.put( BioStudiesField.LINKS, json.findValues("links").stream().mapToLong(
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
                valueMap.put( BioStudiesField.AUTHORS, author);

                String access = !json.has("accessTags") ? "" :
                        StreamSupport.stream(json.get("accessTags").spliterator(),false)
                                .map( s-> s.textValue())
                                .collect(Collectors.joining(" "));
                valueMap.put( BioStudiesField.ACCESS, access.replaceAll("~", ""));

                String value="";
                if(json.has("section") && json.get("section").has("attributes")) {
                    JsonNode attNodes = json.get("section").get("attributes");

                    for(BioStudiesField bsField:BioStudiesField.values()){
                        if(bsField.getType()!= BioStudiesFieldType.FACET)
                            continue;
                        value = StreamSupport.stream(attNodes.spliterator(), false)
                                .filter(jsonNode ->
                                        jsonNode.has("name") && jsonNode.get("name").textValue().equalsIgnoreCase(bsField.getTitle()))
                                .map(s->s.get("value").textValue() )
                                .collect(Collectors.joining(","));
                        valueMap.put(bsField, value);
                    }
                }
                long releaseDateLong = 0L;
                if(json.has("rtime"))
                    releaseDateLong = Long.valueOf(json.get("rtime").asText());
                if(releaseDateLong==0L) {
                    Calendar calendar = Calendar.getInstance();
                    if(!String.valueOf(valueMap.get(BioStudiesField.ACCESS)).contains("public"))
                        calendar.set(2050, 0, 1);
                    releaseDateLong = calendar.getTimeInMillis();
                }
                valueMap.put(BioStudiesField.RELEASE_DATE, releaseDateLong);

                String project = "";
                if(json.has("attributes")) {
                    project = StreamSupport.stream(json.get("attributes").spliterator(), false)
                            .filter(jsonNode ->
                                    jsonNode.has("name") && jsonNode.get("name").textValue().equalsIgnoreCase("attachto"))
                            .map(s -> s.get("value").textValue())
                            .collect(Collectors.joining(","));
                }
                valueMap.put(BioStudiesField.PROJECT, project);
                updateDocument(valueMap);
            } catch (Exception e) {
                System.out.println("Problem indexing " + json);
                e.printStackTrace();
            }
        }

        private void updateDocument(Map<BioStudiesField, Object> valueMap) throws IOException {
            Document doc = new Document();

            //TODO: replace by classes if possible
            String value;
            for (BioStudiesField field: BioStudiesField.values()) {
                try{
                    switch (field.getType()) {
                        case STRING_TOKENIZED:
                            value = valueMap.get(field).toString();
                            doc.add(new TextField(String.valueOf(field), value, Field.Store.YES));
//                        if need sorting uncomment these lines
//                        if(field.isSort())
//                            doc.add( new SortedDocValuesField(String.valueOf(field), new BytesRef(value.length()<256 ? value.toLowerCase():value.substring(0,256).toLowerCase())));
                            break;
                        case STRING_UNTOKENIZED:
                            value = valueMap.get(field).toString();
                            Field unTokenizeField = new Field(String.valueOf(field), value, BioStudiesFieldType.TYPE_NOT_ANALYZED);
                            doc.add(unTokenizeField);
//                        if need sorting uncomment these lines
//                        if(field.isSort())
//                            doc.add( new SortedDocValuesField(String.valueOf(field), new BytesRef(value.length()<256 ? value.toLowerCase():value.substring(0,256).toLowerCase())));

                            break;
                        case LONG:
                            doc.add(new SortedNumericDocValuesField(String.valueOf(field), (Long) valueMap.get(field)));
                            doc.add(new StoredField(String.valueOf(field), valueMap.get(field).toString()));
                            break;
                        case FACET:
                            addFacet(String.valueOf(valueMap.get(field)), field, doc);
                    }
                }catch(Exception ex){
                    logger.error("field name: {}", field.toString(), ex);
                }


            }

            Document facetedDocument = taxonomyManager.getFacetsConfig().build(taxonomyManager.getTaxonomyWriter() ,doc);
            writer.updateDocument(new Term(String.valueOf(BioStudiesField.ID), valueMap.get(BioStudiesField.ACCESSION).toString()), facetedDocument);
        }

        private void addFacet(String value, BioStudiesField field, Document doc){
            if(value==null || value.isEmpty()) {
                value = "n/a";
            }
            for(String subVal:value.split(",")) {
                doc.add(new FacetField(field.toString(), subVal.trim().toLowerCase()));
            }
        }

        private String getTitle(JsonNode json) {
            String title = "";

            try {
                title = StreamSupport.stream(this.json.get("section").get("attributes").spliterator(), false)
                        .filter(jsonNode -> jsonNode.get("name").textValue().equalsIgnoreCase("Title"))
                        .findFirst().get().get("value").textValue().trim();
            } catch (Exception ex1) {
                System.out.println( "Title not found. Trying submission title for " + json.toString().substring(0,100));
                try {
                    title = StreamSupport.stream(this.json.get("attributes").spliterator(), false)
                            .filter(jsonNode -> jsonNode.get("name").textValue().equalsIgnoreCase("Title"))
                            .findFirst().get().get("value").textValue().trim();
                } catch ( Exception ex2) {
                    System.out.println( "Title not found for " + json.toString().substring(0,100));
                }
            }
            return title;
        }
    }



}