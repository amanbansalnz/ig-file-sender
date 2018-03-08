package com.ig.web.converter;


import com.ig.core.model.ActivemqConfiguration;
import com.ig.core.util.AutoRegisteredConverter;
import com.ig.web.requests.OrderRequest;

import org.springframework.stereotype.Component;

@Component
public class OrderRequestConverterToActivemqConfiguration extends AutoRegisteredConverter<OrderRequest, ActivemqConfiguration> {

    @Override
    public ActivemqConfiguration convert(OrderRequest orderRequest) {

        ActivemqConfiguration activemqConfiguration = new ActivemqConfiguration();
        activemqConfiguration.setBrokerUrl(orderRequest.getBrokerUrl());
        activemqConfiguration.setUserName(orderRequest.getUserName());
        activemqConfiguration.setPassword(orderRequest.getPassword());
        activemqConfiguration.setDestinationName(orderRequest.getDestinationName());
        String isQueue = orderRequest.isQueue();
        if (isQueue == null) {
            activemqConfiguration.setIsQueue(false);
        } else {
            activemqConfiguration.setIsQueue(orderRequest.isQueue().equals("true") ? true : false);
        }


        return activemqConfiguration;
    }
}
