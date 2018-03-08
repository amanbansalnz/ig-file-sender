package com.ig.remote.events.service;

import com.ig.core.model.ActivemqConfiguration;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    private static Logger LOGGER = LoggerFactory.getLogger(EventService.class);

    private final JmsTemplate jmsTemplate;
    private EventProducer eventProducer;

    public EventService(JmsTemplate jmsTemplate, EventProducer eventProducer) {
        this.jmsTemplate = jmsTemplate;
        this.eventProducer = eventProducer;
    }

    public void setupEventConfiguration(ActivemqConfiguration activemqConfiguration) {
        LOGGER.info("setting up event configuration");
        setUpJmsTemplate(activemqConfiguration);
        String queue = activemqConfiguration.getDestinationName();
        eventProducer.setQueue(queue);
        eventProducer.setJmsTemplate(jmsTemplate);

        if(!activemqConfiguration.isQueue()){
           jmsTemplate.setPubSubDomain(true);
        }
    }

    private void setUpJmsTemplate(ActivemqConfiguration activemqConfiguration) {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(activemqConfiguration.getBrokerUrl());
        connectionFactory.setUserName(activemqConfiguration.getUserName());
        connectionFactory.setPassword(activemqConfiguration.getPassword());
        jmsTemplate.setConnectionFactory(connectionFactory);
    }

    public EventProducer getEventProducer() {
        return eventProducer;
    }

    public JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }
}
