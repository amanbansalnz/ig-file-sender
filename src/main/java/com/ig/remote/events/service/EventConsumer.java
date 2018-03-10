package com.ig.remote.events.service;


import com.ig.core.model.Order;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.QueueConnectionFactory;
import javax.jms.Session;

@Component
public class EventConsumer implements MessageListener {

    private final Logger LOGGER = LoggerFactory.getLogger(EventConsumer.class);
    private final int NUMBER_OF_MAX_TOPIC_CONSUMERS = 2;
    private MessageConverter jacksonJmsMessageConverter;
    private int numberOfTopicConsumers = 0;
    private int numberOfQueueConsumers = 0;

    public EventConsumer(MessageConverter jacksonJmsMessageConverter) {
        this.jacksonJmsMessageConverter = jacksonJmsMessageConverter;
    }

    @Override
    public void onMessage(Message message) {
        try {
            Order order = (Order) jacksonJmsMessageConverter.fromMessage(message);
            LOGGER.info("Message received >>> {} ", order.toString());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void createConsumer(JmsTemplate jmsTemplate, String queue) {
        try {
            if (numberOfQueueConsumers < 1) {
                ConnectionFactory connectionFactory = jmsTemplate.getConnectionFactory();
                Connection connection = connectionFactory.createConnection();
                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

                MessageConsumer consumer = session.createConsumer(session.createQueue(queue));
                consumer.setMessageListener(this);
                connection.start();
                numberOfQueueConsumers++;
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void createConsumers(JmsTemplate jmsTemplate, String queue) {
        try {
            Connection connection = jmsTemplate.getConnectionFactory().createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            while (numberOfTopicConsumers < NUMBER_OF_MAX_TOPIC_CONSUMERS) {
                session.createConsumer(session.createTopic(queue)).setMessageListener(this);
                numberOfTopicConsumers++;
            }
            connection.start();

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
