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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import uk.ac.ebi.biostudies.api.BioStudiesField;
import uk.ac.ebi.biostudies.api.BioStudiesFieldType;
import uk.ac.ebi.biostudies.lucene.config.IndexConfig;
import uk.ac.ebi.biostudies.lucene.config.TaxonomyManager;
import uk.ac.ebi.biostudies.service.FacetService;
import uk.ac.ebi.biostudies.service.IndexService;
import uk.ac.ebi.biostudies.lucene.config.IndexManager;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

    @PostConstruct
    public void init(){

    }


    @Override
    public void indexAll() {
        Long startTime = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(indexConfig.getThreadCount());
        String inputStudiesFile = indexConfig.getStudiesInputFile();
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
                    logger.info("{} number of docs are indexed", counter);
            }

            executorService.shutdown();
            executorService.awaitTermination(5, TimeUnit.HOURS);
            taxonomyManager.commitTaxonomy();
            indexManager.getIndexWriter().commit();
            indexManager.refreshIndexSearcherAndReader();
            taxonomyManager.refreshTaxonomyReader();
            logger.info("indexing last {} seconds", (System.currentTimeMillis()-startTime)/1000);
//            jnode=facetService.getHecatosFacets();
//            logger.debug(j JsonNodenode.toString());
        }
        catch (Throwable error){
            logger.error("problem in parsing biostudies.json file", error);
        }
    }

    @Override
    public void indexSingleDoc() {

    }

    @Override
    public void deleteDoc() {

    }

    @Override
    public void clearIndex() throws IOException {
        indexManager.getIndexWriter().deleteAll();
        indexManager.getIndexWriter().forceMergeDeletes();
        indexManager.getIndexWriter().commit();

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
                valueMap.put( BioStudiesField.CONTENT, String.join(" ", json.findValuesAsText("value")));
                valueMap.put( BioStudiesField.FILES, json.findValues("files").stream().mapToLong(
                        jsonNode -> jsonNode.findValues("path").size()
                        ).sum()
                );
                valueMap.put( BioStudiesField.LINKS, json.findValues("links").stream().mapToLong(
                        jsonNode -> jsonNode.findValues("url").size()
                        ).sum()
                );
                valueMap.put( BioStudiesField.AUTHORS, !json.get("section").has("subsections") ? "" :
                        StreamSupport.stream(json.get("section").get("subsections").spliterator(),false)
                                .filter(jsonNode ->
                                        jsonNode.has("type") && jsonNode.get("type").textValue().equalsIgnoreCase("Author"))
                                .map(authorNode -> StreamSupport.stream(authorNode.get("attributes").spliterator(),false)
                                        .filter(jsonNode -> jsonNode.get("name").textValue().equalsIgnoreCase("Name"))
                                        .findFirst().get().get("value").textValue())
                                .collect(Collectors.joining(", "))
                );

                valueMap.put( BioStudiesField.ACCESS,  !json.has("accessTags") ? "" :
                        StreamSupport.stream(json.get("accessTags").spliterator(),false)
                                .map( s-> s.textValue())
                                .collect(Collectors.joining(" "))
                );
//                valueMap.put( BioStudiesField.ORGAN, !json.get("section").has("subsections") ? "" :

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

                    String project = json.get("accno").asText("");
                    valueMap.put(BioStudiesField.PROJECT, project);
//                    organ = StreamSupport.stream(attNodes.spliterator(), false)
//                            .filter(jsonNode ->
//                                    jsonNode.has("name") && jsonNode.get("name").textValue().equalsIgnoreCase("organ"))
//                            .map(s->s.get("value").textValue() )
//                            .collect(Collectors.joining(","));
//                    compound = StreamSupport.stream(attNodes.spliterator(), false)
//                            .filter(jsonNode ->
//                                    jsonNode.has("name") && jsonNode.get("name").textValue().equalsIgnoreCase("compound"))
//                            .map(s->s.get("value").textValue() )
//                            .collect(Collectors.joining(","));
//                    dataType = StreamSupport.stream(attNodes.spliterator(), false)
//                            .filter(jsonNode ->
//                                    jsonNode.has("name") && jsonNode.get("name").textValue().equalsIgnoreCase("Data Type"))
//                            .map(s->s.get("value").textValue() )
//                            .collect(Collectors.joining(","));
//                    tech = StreamSupport.stream(attNodes.spliterator(), false)
//                            .filter(jsonNode ->
//                                    jsonNode.has("name") && jsonNode.get("name").textValue().equalsIgnoreCase("Assay Technology Type"))
//                            .map(s->s.get("value").textValue() )
//                            .collect(Collectors.joining(","));
//                    raw = StreamSupport.stream(attNodes.spliterator(), false)
//                            .filter(jsonNode ->
//                                    jsonNode.has("name") && jsonNode.get("name").textValue().equalsIgnoreCase("Raw/Processed"))
//                            .map(s->s.get("value").textValue() )
//                            .collect(Collectors.joining(","));

                }
                if(value!=null && !value.isEmpty())
                    logger.debug(value);



                updateDocument(valueMap);

            } catch (Exception e) {
                System.out.println("Problem indexing " + json);
                e.printStackTrace();
            }
        }

        private void updateDocument(Map<BioStudiesField, Object> valueMap) throws IOException {
            Document doc = new Document();

            //TODO: replace by classes if possible
            for (BioStudiesField field: BioStudiesField.values()) {
                try{
                switch (field.getType()) {
                    case STRING_TOKENIZED:
                        doc.add(new TextField(String.valueOf(field), valueMap.get(field).toString(), Field.Store.YES));
                        break;
                    case STRING_UNTOKENIZED:
                        doc.add(new StringField(String.valueOf(field), valueMap.get(field).toString(), Field.Store.YES));
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
            if(value==null || value.isEmpty())
                value="n/a";
            String []subValues = value.split(",");
            for(String subVal:subValues){
                doc.add(new FacetField(field.toString(), value.trim().toLowerCase()));
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
