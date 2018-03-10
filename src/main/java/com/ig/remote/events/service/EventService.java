package com.ig.remote.events.service;

import com.ig.core.model.ActivemqConfiguration;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    private static Logger LOGGER = LoggerFactory.getLogger(EventService.class);

    private EventProducer eventProducer;
    private EventConsumer eventConsumer;

    @Autowired
    private MessageConverter jacksonJmsMessageConverter;

    public EventService(EventProducer eventProducer, EventConsumer eventConsumer) {
        this.eventProducer = eventProducer;
        this.eventConsumer = eventConsumer;
    }

    public JmsTemplate setupEventConfiguration(ActivemqConfiguration activemqConfiguration, boolean isQueue) {
        LOGGER.info("setting up event configuration");
        String queue = activemqConfiguration.getDestinationName();
        JmsTemplate jmsTemplate = setUpJmsTemplate(activemqConfiguration);

        if(isQueue) {
            eventConsumer.createConsumer(jmsTemplate, queue+1);
        }else{
            jmsTemplate.setPubSubDomain(true);
            eventConsumer.createConsumers(jmsTemplate, queue);
        }
        return jmsTemplate;
    }

    private JmsTemplate setUpJmsTemplate(ActivemqConfiguration activemqConfiguration) {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(activemqConfiguration.getBrokerUrl());
        connectionFactory.setUserName(activemqConfiguration.getUserName());
        connectionFactory.setPassword(activemqConfiguration.getPassword());
        JmsTemplate jms = new JmsTemplate();
        jms.setMessageConverter(jacksonJmsMessageConverter);
        jms.setConnectionFactory(connectionFactory);
       return jms;
    }

    public EventProducer getEventProducer() {
        return eventProducer;
    }

}
