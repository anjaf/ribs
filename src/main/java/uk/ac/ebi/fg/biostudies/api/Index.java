package uk.ac.ebi.fg.biostudies.api;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.store.FSDirectory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static uk.ac.ebi.fg.biostudies.api.BioStudiesFieldType.*;
import static uk.ac.ebi.fg.biostudies.api.Search.INDEX_PATH;
import static uk.ac.ebi.fg.biostudies.api.BioStudiesField.*;


/**
 * Root resource (exposed at "myresource" path)
 */
@SuppressWarnings("Duplicates")
@Path("index")
public class Index {

    public static int THREAD_COUNT = 2;

    public static IndexWriter writer;
    public static ExecutorService executorService;
    public static long startTime;

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public static String createIndex() throws Exception {

        startup();
        String xmlFile = "B:\\.adm\\databases\\beta\\updates\\studies.json";
        int counter = 0;


        try (InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(xmlFile), "UTF-8")) {
            JsonFactory factory = new JsonFactory();
            JsonParser parser = factory.createParser(inputStreamReader);


            JsonToken token = parser.nextToken();
            while (!JsonToken.START_ARRAY.equals(token)) {
                token = parser.nextToken();
            }

            ObjectMapper mapper = new ObjectMapper();
            while (true) { // counter <100) {
                token = parser.nextToken();
                if (!JsonToken.START_OBJECT.equals(token)) {
                    break;
                }
                if (token == null) {
                    break;
                }

                JsonNode submission = mapper.readTree(parser);
                executorService.execute(new JsonDocumentIndexer(submission, writer));
                System.out.println(counter);
                counter++;
            }
        }
        System.out.println(counter + " docs indexed");
        shutdown();
        return "OK";
    }


    private static void shutdown() throws InterruptedException, IOException {
        executorService.shutdown();
        executorService.awaitTermination(5, TimeUnit.HOURS);
        executorService = null;
        if(writer!=null) {
            writer.commit();
            writer.close();
        }
        writer = null;
        System.out.println();
        System.out.println(new Date().getTime() - startTime);

    }

    private static void startup() throws IOException {
        if (writer==null) {
            FSDirectory dir = FSDirectory.open(Paths.get(INDEX_PATH));
            IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
            config.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
            writer = new IndexWriter(dir, config);

            executorService = Executors.newFixedThreadPool(THREAD_COUNT);
            startTime = new Date().getTime();
        }
    }

    static class JsonDocumentIndexer implements Runnable {

        private IndexWriter writer;
        private JsonNode json;

        public JsonDocumentIndexer(JsonNode json, IndexWriter writer) {

            this.writer = writer;
            this.json = json;
        }

        @Override
        public void run() {
            try {

                Map <BioStudiesField, Object> valueMap = new HashMap<>(BioStudiesField.values().length);

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
