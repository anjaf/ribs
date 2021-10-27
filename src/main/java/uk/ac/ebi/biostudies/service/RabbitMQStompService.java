package uk.ac.ebi.biostudies.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import uk.ac.ebi.biostudies.config.SecurityConfig;

/**
 * Ehsan
 */
@Service
@Scope("singleton")
public class RabbitMQStompService implements InitializingBean, DisposableBean {

    private static Logger logger = LogManager.getLogger(RabbitMQStompService.class);
    @Autowired
    SecurityConfig securityConfig;
    @Autowired
    private Environment env;

    @Override
    public void destroy() throws Exception {

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.debug("initiating stomp client service");
        if (!env.getProperty("spring.rabbitmq.stomp.enable", Boolean.class, false)) {
            logger.debug("stomp client is disable");
            return;
        }
        logger.debug("stomp client is enable");
        String url = "ws://%s:%s/ws";
        url = String.format(url, securityConfig.getStompHost(), securityConfig.getStompPort());
        WebSocketClient client = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new StringMessageConverter());
        final StompHeaders stompHeaders = new StompHeaders();
        stompHeaders.add(StompHeaderAccessor.STOMP_LOGIN_HEADER, securityConfig.getStompLoginUser());
        stompHeaders.add(StompHeaderAccessor.STOMP_PASSCODE_HEADER, securityConfig.getStompPassword());
        stompHeaders.add(StompHeaderAccessor.STOMP_ACCEPT_VERSION_HEADER, "1.1,1.2");
        RabbitMQStompSessionHandler sessionHandler = new RabbitMQStompSessionHandler();
        stompClient.connect(url, new WebSocketHttpHeaders(), stompHeaders, sessionHandler);
        logger.debug("stomp client going to connect");
    }

    private class RabbitMQStompSessionHandler extends StompSessionHandlerAdapter {
        @Override
        public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
            String submissionPartialQueue = env.getProperty("partial.submission.rabbitmq.queue", String.class, "/queue/submission-submitted-partials-queue");
            if(submissionPartialQueue.indexOf("/queue/")<0)
                submissionPartialQueue = "/queue/"+submissionPartialQueue;
            logger.debug("stomp connection: session:{} \t server:{}",connectedHeaders.get("session"),connectedHeaders.get("server"));
            session.subscribe(submissionPartialQueue, this);
            logger.debug("queue name {}", submissionPartialQueue);
            logger.debug("stomp client connected successfully!");
        }

        @Override
        public void handleTransportError(StompSession session, Throwable exception) {
            logger.error("Got a transport exception", exception);
        }

        @Override
        public void handleFrame(StompHeaders headers, Object payload) {
            logger.info("Received a frame!");
            String msg = (String) payload;
            logger.info("Received : {}" , msg);
        }

        @Override
        public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
            logger.error("Got an exception", exception);
        }
    }


}