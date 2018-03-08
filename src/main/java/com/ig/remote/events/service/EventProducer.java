package com.ig.remote.events.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class EventProducer {

    private static Logger LOGGER = LoggerFactory.getLogger(EventProducer.class);
    private JmsTemplate jmsTemplate;
    private String queue = "order-queue";

    public static void setLogger(Logger LOGGER) {
        EventProducer.LOGGER = LOGGER;
    }

    public void sendMessage(Object myMessage) {
        LOGGER.info("Sending message >>> {} ", myMessage);
        jmsTemplate.convertAndSend(queue, myMessage);
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }
}
