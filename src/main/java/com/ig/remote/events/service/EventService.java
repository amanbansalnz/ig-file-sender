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
    private EventConsumer eventConsumer;

    public EventService(JmsTemplate jmsTemplate, EventProducer eventProducer, EventConsumer eventConsumer) {
        this.jmsTemplate = jmsTemplate;
        this.eventProducer = eventProducer;
        this.eventConsumer = eventConsumer;
    }

    public void setupEventConfiguration(ActivemqConfiguration activemqConfiguration) {
        LOGGER.info("setting up event configuration");
        setUpJmsTemplate(activemqConfiguration);
        String queue = activemqConfiguration.getDestinationName();
        eventProducer.setQueue(queue);
        eventProducer.setJmsTemplate(jmsTemplate);

        if (activemqConfiguration.isQueue()) {
            eventConsumer.createConsumer(jmsTemplate, queue);
        } else {
            jmsTemplate.setPubSubDomain(true);
            eventConsumer.createConsumers(jmsTemplate, queue);
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
