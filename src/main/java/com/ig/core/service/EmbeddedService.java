package com.ig.core.service;

import org.apache.activemq.broker.BrokerService;

public class EmbeddedService {

    public static void emdeddedMq() throws Exception {
        BrokerService broker = new BrokerService();
        broker.setBrokerName("embedded-activemq");
        broker.addConnector("tcp://localhost:61666");
        broker.setUseJmx(false);
        broker.setAdvisorySupport(false);
        broker.setPersistent(false);
        broker.start();
    }
}
