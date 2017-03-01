package uk.com.ebi.biostudy.service.impl;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import uk.com.ebi.biostudy.api.BioStudiesField;
import uk.com.ebi.biostudy.lucene.config.BioIndexManager;
import uk.com.ebi.biostudy.lucene.config.IndexConfig;
import uk.com.ebi.biostudy.service.IndexService;

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

import static uk.com.ebi.biostudy.api.BioStudiesField.*;
import static uk.com.ebi.biostudy.api.BioStudiesField.ACCESSION;

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
    BioIndexManager bioIndexManager;

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
                executorService.execute(new JsonDocumentIndexer(submission, bioIndexManager.getIndexWriter()));
                if(++counter%1000==0)
                    logger.info("{} number of docs are indexed", counter);
            }

            executorService.shutdown();
            executorService.awaitTermination(5, TimeUnit.HOURS);
            bioIndexManager.getIndexWriter().commit();

            logger.info("indexing last {} seconds", (System.currentTimeMillis()-startTime)/1000);
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
    public void clearIndex() {

    }


    public static class JsonDocumentIndexer implements Runnable {

        private IndexWriter writer;
        private JsonNode json;

        public JsonDocumentIndexer(JsonNode json, IndexWriter writer) {

            this.writer = writer;
            this.json = json;
        }

        @Override
        public void run() {
            try {

                Map<BioStudiesField, Object> valueMap = new HashMap<>(BioStudiesField.values().length);

                valueMap.put( ID, json.get("accno").textValue() );
                valueMap.put( ACCESSION, valueMap.get(ID));
                valueMap.put( TYPE, json.get("section").get("type").textValue().toLowerCase());
                valueMap.put( TITLE, getTitle(json));
                valueMap.put( CONTENT, String.join(" ", json.findValuesAsText("value")));
                valueMap.put( FILES, json.findValues("files").stream().mapToLong(
                        jsonNode -> jsonNode.findValues("path").size()
                        ).sum()
                );
                valueMap.put( LINKS, json.findValues("links").stream().mapToLong(
                        jsonNode -> jsonNode.findValues("url").size()
                        ).sum()
                );
                valueMap.put( AUTHORS, !json.get("section").has("subsections") ? "" :
                        StreamSupport.stream(json.get("section").get("subsections").spliterator(),false)
                                .filter(jsonNode ->
                                        jsonNode.has("type") && jsonNode.get("type").textValue().equalsIgnoreCase("Author"))
                                .map(authorNode -> StreamSupport.stream(authorNode.get("attributes").spliterator(),false)
                                        .filter(jsonNode -> jsonNode.get("name").textValue().equalsIgnoreCase("Name"))
                                        .findFirst().get().get("value").textValue())
                                .collect(Collectors.joining(", "))
                );

                valueMap.put( ACCESS,  !json.has("accessTags") ? "" :
                        StreamSupport.stream(json.get("accessTags").spliterator(),false)
                                .map( s-> s.textValue())
                                .collect(Collectors.joining(" "))
                );

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
                switch (field.getType()) {
                    case STRING_TOKENIZED:
                        doc.add(new TextField(String.valueOf(field), valueMap.get(field).toString(), Field.Store.YES));
                        break;
                    case STRING_UNTOKENIZED:
                        doc.add(new StringField( String.valueOf(field), valueMap.get(field).toString(), Field.Store.YES));
                        break;
                    case LONG:
                        doc.add(new SortedNumericDocValuesField( String.valueOf(field), (Long)valueMap.get(field)));
                        doc.add(new StoredField( String.valueOf(field), valueMap.get(field).toString()));
                        break;
                }
            }

            writer.updateDocument(new Term(String.valueOf(ID), valueMap.get(ACCESSION).toString()), doc);
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
