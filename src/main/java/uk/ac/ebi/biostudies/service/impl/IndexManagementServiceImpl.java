package uk.ac.ebi.biostudies.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.index.DirectoryReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import uk.ac.ebi.biostudies.api.util.analyzer.AnalyzerManager;
import uk.ac.ebi.biostudies.api.util.parser.ParserManager;
import uk.ac.ebi.biostudies.config.EFOConfig;
import uk.ac.ebi.biostudies.config.IndexConfig;
import uk.ac.ebi.biostudies.config.IndexManager;
import uk.ac.ebi.biostudies.config.TaxonomyManager;
import uk.ac.ebi.biostudies.service.IndexManagementService;
import uk.ac.ebi.biostudies.service.RabbitMQStompService;
import uk.ac.ebi.biostudies.service.impl.efo.Ontology;

import java.io.InputStream;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
@Scope("singleton")
public class IndexManagementServiceImpl implements IndexManagementService {

    private final static Logger LOGGER = LogManager.getLogger(IndexManagementServiceImpl.class.getName());
    private static final AtomicBoolean closed = new AtomicBoolean(false);


    @Autowired
    IndexManager indexManager;
    @Autowired
    TaxonomyManager taxonomyManager;
    @Autowired
    AnalyzerManager analyzerManager;
    @Autowired
    ParserManager parserManager;
    @Autowired
    IndexConfig indexConfig;
    @Autowired
    RabbitMQStompService rabbitMQStompService;
    @Autowired
    EFOConfig eFOConfig;
    @Autowired
    Ontology ontology;

    @Autowired
    private Environment env;

    @Override
    public synchronized boolean isClosed() {
        return closed.get();
    }

    @Scheduled(fixedDelayString = "${schedule.stomp.isalive:300000}", initialDelay = 600000)
    public void webSocketWatchDog() {
        if (!env.getProperty("spring.rabbitmq.stomp.enable", Boolean.class, false) || rabbitMQStompService.isSessionConnected() || isClosed())
            return;
        openWebsocket();
        LOGGER.info("Failed Websocket Connection recovered by watchDog!");
    }

    @Override
    public synchronized void closeWebsocket() {
        if (!env.getProperty("spring.rabbitmq.stomp.enable", Boolean.class, false))
            return;
        rabbitMQStompService.stopWebSocket();
        closed.set(true);
    }

    @Override
    public synchronized void openWebsocket() {
        if (!env.getProperty("spring.rabbitmq.stomp.enable", Boolean.class, false))
            return;
        rabbitMQStompService.startWebSocket();
        closed.set(false);
    }


    @Override
    public void stopAcceptingSubmissionMessagesAndCloseIndices() {
        rabbitMQStompService.stopWebSocket();
        indexManager.closeIndices();
    }

    @Override
    public void openEfoIndexAndLoadOntology() {
        try {
            indexManager.openEfoIndex();
            if (!DirectoryReader.indexExists(indexManager.getEfoIndexDirectory())) {
                try (InputStream resourceInputStream = (new ClassPathResource(eFOConfig.getLocalOwlFilename())).getInputStream()) {
                    ontology.update(resourceInputStream);
                    LOGGER.info("EFO loading completed");
                } catch (Exception ex) {
                    LOGGER.error("EFO file not found", ex);
                }
            }
        } catch (Throwable exception) {
            LOGGER.error("EFO loading problem", exception);
        }
    }


    @Override
    public void openIndicesWritersAndSearchersStartStomp() {
        try {
            indexManager.openIndicesWritersAndSearchers();
            openWebsocket();
            openEfoIndexAndLoadOntology();
        } catch (Throwable error) {
            LOGGER.error(error);
        }

    }

}
