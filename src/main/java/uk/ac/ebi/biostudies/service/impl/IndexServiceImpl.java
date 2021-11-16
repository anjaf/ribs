package uk.ac.ebi.biostudies.service.impl;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import uk.ac.ebi.biostudies.api.util.Constants;
import uk.ac.ebi.biostudies.api.util.analyzer.AttributeFieldAnalyzer;
import uk.ac.ebi.biostudies.api.util.parser.ParserManager;
import uk.ac.ebi.biostudies.config.IndexConfig;
import uk.ac.ebi.biostudies.config.IndexManager;
import uk.ac.ebi.biostudies.config.TaxonomyManager;
import uk.ac.ebi.biostudies.service.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static uk.ac.ebi.biostudies.api.util.Constants.Fields;
import static uk.ac.ebi.biostudies.api.util.Constants.STUDIES_JSON_FILE;

/**
 * Created by ehsan on 27/02/2017.
 */


@Service
@Scope("singleton")

public class IndexServiceImpl implements IndexService {

    public static final FieldType TYPE_NOT_ANALYZED = new FieldType();
    static {
        TYPE_NOT_ANALYZED.setIndexOptions(IndexOptions.DOCS);
        TYPE_NOT_ANALYZED.setTokenized(false);
        TYPE_NOT_ANALYZED.setStored(true);
    }

    public static AtomicInteger ActiveExecutorService = new AtomicInteger(0);

    private Logger logger = LogManager.getLogger(IndexServiceImpl.class.getName());

    private static  BlockingQueue<String> indexFileQueue = new LinkedBlockingQueue<>();

    private static AtomicBoolean closed = new AtomicBoolean(false);

    @Autowired
    private Environment env;

    @Autowired
    IndexConfig indexConfig;

    @Autowired
    IndexManager indexManager;

    @Autowired
    FileIndexService fileIndexService;

    @Autowired
    SearchService searchService;

    @Autowired
    TaxonomyManager taxonomyManager;

    @Autowired
    FacetService facetService;

    @Autowired
    ParserManager parserManager;

    @Autowired
    private RabbitListenerEndpointRegistry rabbitListenerEndpointRegistry;

    @Override
    public synchronized boolean isClosed() {
        return closed.get();
    }

    @Override
    public synchronized void close() {
        if(!env.getProperty("spring.rabbitmq.listener.simple.auto-startup", Boolean.class, false))
            return;
        MessageListenerContainer listenerContainer = rabbitListenerEndpointRegistry.getListenerContainer(PartialUpdateListener.PARTIAL_UPDATE_LISTENER);
        if(listenerContainer.isRunning()) {
            listenerContainer.stop();
            closed.set(true);
        }
    }

    @Override
    public synchronized void open() {
        if(!env.getProperty("spring.rabbitmq.listener.simple.auto-startup", Boolean.class, false))
            return;
        MessageListenerContainer listenerContainer = rabbitListenerEndpointRegistry.getListenerContainer(PartialUpdateListener.PARTIAL_UPDATE_LISTENER);
        if(!listenerContainer.isRunning()) {
            listenerContainer.start();
            closed.set(false);
        }
    }

    @Override
    public void indexAll(InputStream inputStream, boolean removeFileDocuments) throws IOException {

        Long startTime = System.currentTimeMillis();
        ExecutorService executorService = new ThreadPoolExecutor(indexConfig.getThreadCount(), indexConfig.getThreadCount(),
                60, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(indexConfig.getQueueSize()), new ThreadPoolExecutor.CallerRunsPolicy());
        ActiveExecutorService.incrementAndGet();
        int counter = 0;
        try (InputStreamReader inputStreamReader = new InputStreamReader( inputStream , "UTF-8")) {
            JsonFactory factory = new JsonFactory();
            JsonParser parser = factory.createParser(inputStreamReader);


            JsonToken token = parser.nextToken();
            while (token!=null && !JsonToken.START_ARRAY.equals(token)) {
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
                executorService.execute(new JsonDocumentIndexer(submission, taxonomyManager, indexManager, fileIndexService, removeFileDocuments, parserManager));
                if(++counter % 10000==0) {
                    logger.info("{} docs indexed", counter);
                }
            }
            while (token != null && token != JsonToken.END_OBJECT) {
                token = parser.nextToken();
            }

            Map<String,String> commitData = new HashMap<>();
            commitData.put("updateTime", Long.toString (new Date().getTime()) );
            indexManager.getIndexWriter().setLiveCommitData(commitData.entrySet());

            executorService.shutdown();
            executorService.awaitTermination(5, TimeUnit.MINUTES);
            indexManager.commitTaxonomy();
            indexManager.getIndexWriter().commit();
            indexManager.refreshIndexSearcherAndReader();
            indexManager.refreshTaxonomyReader();
            logger.info("Indexing lasted {} seconds", (System.currentTimeMillis()-startTime)/1000);
            ActiveExecutorService.decrementAndGet();
            searchService.clearStatsCache();
        }
        catch (Throwable error){
            logger.error("problem in parsing partial update", error);
        } finally {
            //logger.debug("Deleting temp file {}", inputStudiesFilePath);
            //Files.delete(Paths.get(inputStudiesFilePath));
        }
    }


    @Override
    public void indexOne(JsonNode submission, boolean removeFileDocuments) throws IOException {
        //TODO: Remove executor service if not needed
        Long startTime = System.currentTimeMillis();
        ExecutorService executorService = new ThreadPoolExecutor(indexConfig.getThreadCount(), indexConfig.getThreadCount(),
                60, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(indexConfig.getQueueSize()), new ThreadPoolExecutor.CallerRunsPolicy());
        ActiveExecutorService.incrementAndGet();
        executorService.execute(new JsonDocumentIndexer(submission, taxonomyManager, indexManager, fileIndexService, removeFileDocuments, parserManager));
        executorService.shutdown();
        try {
            Map<String,String> commitData = new HashMap<>();
            commitData.put("updateTime", Long.toString (new Date().getTime()) );
            indexManager.getIndexWriter().setLiveCommitData(commitData.entrySet());

            executorService.awaitTermination(5, TimeUnit.HOURS);
            indexManager.commitTaxonomy();
            indexManager.getIndexWriter().commit();
            indexManager.refreshIndexSearcherAndReader();
            indexManager.refreshTaxonomyReader();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ActiveExecutorService.decrementAndGet();
        searchService.clearStatsCache();
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
        searchService.clearStatsCache();
    }

    @Override
    public void clearIndex(boolean commit) throws IOException {
        indexManager.getIndexWriter().deleteAll();
        indexManager.getIndexWriter().forceMergeDeletes();
        if(commit) {
            indexManager.getIndexWriter().commit();
            indexManager.refreshIndexSearcherAndReader();
        }
        indexManager.resetTaxonomyWriter();
        searchService.clearStatsCache();
    }

    public synchronized String getCopiedSourceFile(String jsonFileName) throws IOException {
        File destFile = new File(System.getProperty("java.io.tmpdir"), jsonFileName);
        String sourceLocation = indexConfig.getStudiesInputFile();
        if (isNotBlank(sourceLocation)) {
            if (jsonFileName != null && !jsonFileName.isEmpty()) {
                sourceLocation = sourceLocation.replaceAll(STUDIES_JSON_FILE, jsonFileName);
            }
            File srcFile = new File(sourceLocation);
            logger.info("Making a local copy  of {} at {}", srcFile.getAbsolutePath(), destFile.getAbsolutePath());
            com.google.common.io.Files.copy(srcFile, destFile);
        }
        return destFile.getAbsolutePath();
    }

    @Override
    public void destroy() {

    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Async
    public void processFileForIndexing() {
        logger.debug("Initializing File Queue for Indexing");
        while (true) {
            String filename = null;
            String inputStudiesFilePath = null;
            try {
                filename = indexFileQueue.take();
                logger.log(Level.INFO, "Started indexing {}. {} files left in the queue.", filename, indexFileQueue.size());
                boolean removeFileDocuments = true;
                if(indexManager.getIndexWriter()==null || !indexManager.getIndexWriter().isOpen()){
                    logger.log(Level.INFO,"IndexWriter was closed trying to construct a new IndexWriter");
                    indexManager.refreshIndexWriterAndWholeOtherIndices();
                    Thread.sleep(30000);
                    indexFileQueue.put(filename);
                    continue;
                }
                if (filename == null || filename.isEmpty() || filename.equalsIgnoreCase(Constants.STUDIES_JSON_FILE) || filename.equalsIgnoreCase("default"))  {
                    close();
                    clearIndex(false);
                    filename = Constants.STUDIES_JSON_FILE;
                    removeFileDocuments = false;
                }
                inputStudiesFilePath = getCopiedSourceFile(filename);
                indexAll(new FileInputStream(inputStudiesFilePath), removeFileDocuments);
            } catch (Exception e) {
                e.printStackTrace();
                logger.log(Level.ERROR, e);
            } finally {
                if(closed.get())
                    open();
                try {
                    Files.delete(Paths.get(inputStudiesFilePath));
                } catch (Throwable e) {
                    logger.error("Cannot delete {}", inputStudiesFilePath);
                }
            }
            logger.log(Level.INFO, "Finished indexing {}", filename);
        }
    }

    public BlockingQueue<String> getIndexFileQueue() {
        return indexFileQueue;
    }

}
