package com.ig.remote.events.service;


import org.apache.activemq.broker.region.Queue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class EventProducer {

    private static Logger LOGGER = LoggerFactory.getLogger(EventProducer.class);

    public static void setLogger(Logger LOGGER) {
        EventProducer.LOGGER = LOGGER;
    }

    public void sendMessage(JmsTemplate jmsTemplate, Object myMessage, String queue) {
        LOGGER.info("Sending message >>> {} ", myMessage);
        jmsTemplate.convertAndSend(queue, myMessage);
    }
}
