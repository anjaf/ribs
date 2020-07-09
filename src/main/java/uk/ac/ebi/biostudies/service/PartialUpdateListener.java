package uk.ac.ebi.biostudies.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.ac.ebi.biostudies.service.impl.IndexServiceImpl;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

@Component
public class PartialUpdateListener {

    private Logger logger = LogManager.getLogger(IndexServiceImpl.class.getName());

    @Autowired
    IndexService indexService;

    @RabbitListener(queues = "${partial.submission.rabbitmq.queue}")
    public void receivedMessage(JsonNode msg) {
        try {
            String url = msg.get("extTabUrl").asText();
            JsonNode submission = new ObjectMapper().readTree(new URL(url));
            indexService.indexOne(submission, true);
        } catch (Exception ex) {
            logger.error("Error parsing message", ex);
        }
    }

}
