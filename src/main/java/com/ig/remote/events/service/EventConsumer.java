package com.ig.remote.events.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.Message;
import javax.jms.MessageListener;

public class EventConsumer implements MessageListener{

    private final Logger LOGGER = LoggerFactory.getLogger(EventConsumer.class);



    @Override
    public void onMessage(Message message) {

    }
}
