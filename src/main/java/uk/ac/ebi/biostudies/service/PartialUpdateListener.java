package uk.ac.ebi.biostudies.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import uk.ac.ebi.biostudies.service.impl.IndexServiceImpl;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

@Component
@Order()
public class PartialUpdateListener {

    private Logger logger = LogManager.getLogger(IndexServiceImpl.class.getName());
    public static final String PARTIAL_UPDATE_LISTENER = "partialUpdateListener";

    @Autowired
    IndexService indexService;

    @Autowired
    MongoDBService mongoDBService;

    @RabbitListener(queues = "${partial.submission.rabbitmq.queue}", id = PARTIAL_UPDATE_LISTENER)
    public void receivedMessage(JsonNode msg) throws IOException, InterruptedException {
        try {
            String url = msg.get("extTabUrl").asText();
            String acc = msg.get("accNo").asText();
            JsonNode submission = new ObjectMapper().readTree(new URL(url));
            indexService.indexOne(submission, true);
            mongoDBService.replaceOne(submission);
            logger.debug("{} updated", acc);
        } catch (Exception ex) {
            logger.error("Error parsing message {}", msg, ex);
            Thread.sleep(30000);
            throw ex;
        }
    }

}
