//package com.ig.remote.events.service;
//
//import com.ig.core.model.Order;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.slf4j.Logger;
//import org.springframework.jms.core.JmsTemplate;
//
//import static org.mockito.Mockito.verify;
//import static util.TestUtil.createOrder;
//
//public class EventProducerTest {
//
//    public static final String ORDER_QUEUE = "order-queue";
//    private EventProducer eventProducer;
//
//    @Mock
//    private JmsTemplate mockJmsTemplate;
//
//    @Mock
//    private Logger mockLogger;
//
//    @Before
//    public void setUp() throws Exception {
//        MockitoAnnotations.initMocks(this);
//        eventProducer = new EventProducer();
//        eventProducer.setJmsTemplate(mockJmsTemplate);
//        eventProducer.setQueue(ORDER_QUEUE);
//        eventProducer.setLogger(mockLogger);
//    }
//
//    @Test
//    public void sendMessage() throws Exception {
//        Order order = createOrder();
//        eventProducer.sendMessage(order);
//        verify(mockJmsTemplate).convertAndSend(ORDER_QUEUE, order);
//    }
//}